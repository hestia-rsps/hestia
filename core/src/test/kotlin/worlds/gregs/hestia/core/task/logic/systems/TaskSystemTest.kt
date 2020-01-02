package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.task.api.*
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.ReusableTask
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.context.EntityContext
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@ExtendWith(MockKExtension::class)
internal class TaskSystemTest : MockkGame() {

    @SpyK
    var system = TaskSystem()

    @SpyK
    var component = TaskQueue()

    @RelaxedMockK
    lateinit var task: Task

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component)
        tick()
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system)
    }

    @Test
    fun `Insert sets entity context`() {
        //When
        tick()
        //Then
        verify { component.context = EntityContext(actualWorld, 0) }
    }

    @Test
    fun `Process activates all tasks`() {
        //Given
        val queue = mockk<SuspendableQueue>(relaxed = true)
        val active = spyk(LinkedList<Task>())
        every { component.active } returns active
        val list: Deque<InactiveTask<Unit>> = LinkedList<InactiveTask<Unit>>()
        list.push(InactiveTask(ReusableTask(TaskPriority.Low, queue), Unit))
        val inactive = spyk(list)
        val details = spyk(LinkedList<Task>())
        every { component.inactiveTasks } returns (inactive as Deque<InactiveTask<*>>)
        every { component.active } returns details
        every { system.processTask(0, component, any(), any(), Unit) } answers {}
        mockkStatic("kotlin.coroutines.ContinuationKt")
        //When
        tick()
        //Then
        verifyOrder {
            component.active
            queue.createCoroutine(any(), any())
            system.processTask(0, component, any(), any(), Unit)
        }
    }

    @Test
    fun `Strong task clears all on process`() {
        //Given
        val queue = mockk<SuspendableQueue>(relaxed = true)
        val active = spyk(LinkedList<Task>())
        val list: Deque<InactiveTask<Unit>> = LinkedList<InactiveTask<Unit>>()
        list.push(InactiveTask(ReusableTask(TaskPriority.High, queue), Unit))
        val inactive = spyk(list)
        every { component.inactiveTasks } returns (inactive as Deque<InactiveTask<*>>)
        every { component.active } returns active
        every { system.cancelAll(any(), any()) } answers {}
        every { system.processTask<Any>(any(), any(), any(), any(), any()) } answers {}
        mockkStatic("kotlin.coroutines.ContinuationKt")
        tick()
        //When
        tick()
        //Then
        verify {
            system.cancelAll(0, TaskCancellation.Priority)
            component.active
            queue.createCoroutine(any(), any())
            system.processTask(0, component, any(), any(), Unit)
        }
    }

    @Test
    fun `Suspension returns first in queue`() {
        //Given
        val active = spyk(LinkedList<Task>())
        every { component.active } returns active
        every { active.peek() } returns task
        //When
        val result = system.getSuspension(0)
        //Then
        verifyOrder {
            component.active
            active.peek()
        }
        assertEquals(task.suspension, result)
    }

    @Test
    fun `Suspension can return null`() {
        //Given
        val active = spyk(LinkedList<Task>())
        every { component.active } returns active
        every { active.peek() } returns null
        //When
        val result = system.getSuspension(0)
        //Then
        verifyOrder {
            component.active
            active.peek()
        }
        assertEquals(null, result)
    }

    @Test
    fun `Resume without component returns false`() {
        //Given
        //When
        val result = system.resume(0, task.suspension!!, mockk(relaxed = true))
        //Then
        assertEquals(false, result)
    }

    @Test
    fun `Resume without tasks returns false`() {
        //Given
        every { component.active } returns LinkedList()
        //When
        val result = system.resume(0, task.suspension!!, mockk(relaxed = true))
        //Then
        assertEquals(false, result)
    }

    @Test
    fun `Process task resumes continuation`() {
        //Given
        every { component.active } returns LinkedList()
        val continuation: Continuation<Unit> = mockk(relaxed = true)
        //When
        system.processTask(0, component, task, continuation, Unit)
        //Then
        verify { continuation.resume(Unit) }
    }

    @Test
    fun `Completed task removed`() {
        //Given
        val list = spyk(LinkedList<Task>())
        list.push(task)
        every { component.active } returns list
        every { task.isCompleted } returns true
        val continuation: Continuation<Unit> = mockk(relaxed = true)
        //When
        system.processTask(0, component, task, continuation, Unit)
        //Then
        verifyOrder {
            continuation.resume(Unit)
            component.active
            list.remove(task)
        }
    }

    @Test
    fun `Cancelled task removed`() {
        //Given
        val list = spyk(LinkedList<Task>())
        list.push(task)
        every { component.active } returns list
        every { task.isCancelled } returns true
        val continuation: Continuation<Unit> = mockk(relaxed = true)
        //When
        system.processTask(0, component, task, continuation, Unit)
        //Then
        verifyOrder {
            continuation.resume(Unit)
            component.active
            list.remove(task)
        }
    }

    @Test
    fun `Active task event`() {
        //Given
        val continuation: Continuation<Unit> = mockk(relaxed = true)
        val suspension: TaskType<Unit> = mockk(relaxed = true)
        every { task.isActive } returns true
        every { task.suspension } returns suspension
        //When
        system.processTask(0, component, task, continuation, Unit)
        //Then
        verify {
            continuation.resume(Unit)
            es.dispatch(ProcessTaskSuspension(0, suspension))
        }
    }

    @Test
    fun `Cancel resumes with exception`() {
        //Given
        val list = spyk(LinkedList<Task>())
        list.push(task)
        every { component.active } returns list
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(0, cancellation)
        //Then
        verifyOrder {
            component.active
            task.resumeWithException(cancellation)
        }
    }

    @Test
    fun `Cancel ignores wrong entity id`() {
        //Given
        val list = spyk(LinkedList<Task>())
        list.push(task)
        every { component.active } returns list
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(2, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel ignores empty queue`() {
        //Given
        every { component.active } returns LinkedList()
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(0, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel all resumes with exception`() {
        //Given
        val list = spyk(LinkedList<Task>())
        list.push(mockk())
        list.push(task)
        every { component.active } returns list
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(0, cancellation)
        //Then
        verifyOrder {
            component.active
            task.resumeWithException(cancellation)
        }
    }

    @Test
    fun `Cancel all ignores wrong entity id`() {
        //Given
        val list = spyk(LinkedList<Task>())
        list.push(task)
        every { component.active } returns list
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(2, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel all ignores empty queue`() {
        //Given
        every { component.active } returns LinkedList()
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(0, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Activate task`() {
        //Given
        val list: Deque<InactiveTask<Unit>> = LinkedList<InactiveTask<Unit>>()
        val inactive = spyk(list)
        every { component.inactiveTasks } returns (inactive as Deque<InactiveTask<*>>)
        val block: SuspendableQueue = mockk()

        //When
        system.activateTask(0, InactiveTask(ReusableTask(TaskPriority.Low, block), Unit))
        //Then
        verifyOrder {
            inactive.push(any())
        }
    }

    @Test
    fun `Activate task with invalid entity id`() {
        //Given
        val inactive = spyk(LinkedList<Task>())
        val details = spyk(LinkedList<Pair<TaskPriority, Continuation<Unit>>>())
//        every { component.inactiveTasks } returns inactive
//        every { component.activateDetails } returns details
        val block: SuspendableQueue = mockk()
        //When
//        system.activateTask(2, TaskPriority.Low, block)
        //Then
        verify(exactly = 0) { inactive.push(any()) }
        assertEquals(0, details.size)
    }
}