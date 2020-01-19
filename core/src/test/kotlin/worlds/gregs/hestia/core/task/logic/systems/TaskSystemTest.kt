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
import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.TaskSuspension
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.api.Resendable
import worlds.gregs.hestia.core.task.model.components.TaskQueue
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
    fun `Process activates all suspended tasks`() {
        //Given
        val queue = mockk<SuspendableQueue>(relaxed = true)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        val deque = spyk<Deque<Task>>(LinkedList())
        active[0] = deque
        every { component.active } returns active
        val inactive = spyk(LinkedList<InactiveTask>())
        inactive.push(InactiveTask(0, queue))
        every { component.inactiveTasks } returns inactive
        every { system.processTask(0, any(), any(), Unit) } answers {}
        mockkStatic("kotlin.coroutines.ContinuationKt")
        //When
        tick()
        //Then
        verifyOrder {
            inactive.poll()
            queue.createCoroutine(any(), any())
            system.processTask(0, any(), any(), Unit)
            component.active
            deque.push(any())
        }
    }

    @Test
    fun `Suspension returns first in queue`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        every { system.resend(any(), any()) } answers {}
        every { system.purge(any()) } returns deque
        every { deque.peek() } returns task
        //When
        val result = system.getSuspension(0)
        //Then
        verifyOrder {
            system.purge(component)
            deque.peek()
        }
        assertEquals(task.suspension, result)
    }

    @Test
    fun `Suspension can return null`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        every { system.resend(any(), any()) } answers {}
        every { system.purge(any()) } returns deque
        every { deque.peek() } returns null
        //When
        val result = system.getSuspension(0)
        //Then
        verifyOrder {
            system.purge(component)
            deque.peek()
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
        every { component.active } returns spyk(TreeMap())
        //When
        val result = system.resume(0, task.suspension!!, mockk(relaxed = true))
        //Then
        assertEquals(false, result)
    }

    @Test
    fun `Process task resumes continuation`() {
        //Given
        every { component.active } returns spyk(TreeMap())
        val continuation: Continuation<Unit> = mockk(relaxed = true)
        //When
        system.processTask(0, task, continuation, Unit)
        //Then
        verify { continuation.resume(Unit) }
    }

    @Test
    fun `Active task event`() {
        //Given
        val continuation: Continuation<Unit> = mockk(relaxed = true)
        val suspension: TaskSuspension<Unit> = mockk(relaxed = true)
        every { task.isActive } returns true
        every { task.suspension } returns suspension
        //When
        system.processTask(0, task, continuation, Unit)
        //Then
        verify {
            continuation.resume(Unit)
            es.perform(0, ProcessTaskSuspension(suspension))
        }
    }

    @Test
    fun `Cancel resumes with exception`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        active[0] = deque
        every { component.active } returns active
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
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        active[0] = deque
        every { component.active } returns active
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(2, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel ignores empty queue`() {
        //Given
        every { component.active } returns spyk(TreeMap())
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancel(0, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel all resumes with exception`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(mockk(relaxed = true))
        deque.push(task)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        active[0] = deque
        every { component.active } returns active
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancelAll(0, cancellation)
        //Then
        verifyOrder {
            component.active
            task.resumeWithException(cancellation)
        }
    }

    @Test
    fun `Cancel all ignores wrong entity id`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        active[0] = deque
        every { component.active } returns active
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancelAll(2, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel all ignores empty queue`() {
        //Given
        every { component.active } returns spyk(TreeMap())
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancelAll(0, cancellation)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel all ignores higher priority`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        active[1] = deque
        every { component.active } returns active
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancelAll(0, cancellation, 0)
        //Then
        verify(exactly = 0) { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Cancel all removes equal priority`() {
        //Given
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        active[0] = deque
        every { component.active } returns active
        val cancellation = TaskCancellation.Cancellation("Unit test")
        //When
        system.cancelAll(0, cancellation, 0)
        //Then
        verify { task.resumeWithException(cancellation) }
    }

    @Test
    fun `Activate task pushes tail of queue`() {
        //Given
        val inactive = spyk(LinkedList<InactiveTask>())
        every { component.inactiveTasks } returns inactive
        val block: SuspendableQueue = mockk(relaxed = true)
        //When
        system.activateTask(0, InactiveTask(0, block))
        //Then
        verifyOrder {
            inactive.addLast(any())
        }
    }

    @Test
    fun `Activate task with invalid entity id`() {
        //Given
        val inactive = spyk(LinkedList<InactiveTask>())
        every { component.inactiveTasks } returns inactive
        val block: SuspendableQueue = mockk(relaxed = true)
        //When
        system.activateTask(2, InactiveTask(0, block))
        //Then
        verify(exactly = 0) { inactive.push(any()) }
    }

    @Test
    fun `Purge returns first queue`() {
        //Given
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        active[1] = deque
        val deque2 = spyk<Deque<Task>>(LinkedList())
        val task2 = mockk<Task>(relaxed = true)
        deque2.push(task2)
        active[0] = deque2
        every { task2.isCompleted } returns false
        every { task.isCompleted } returns false
        every { component.active } returns active
        //When
        val result = system.purge(component)
        //Then
        assertEquals(deque, result)
    }

    @Test
    fun `Purge removes only top completed tasks`() {
        //Given
        val active = spyk(TreeMap<Int, Deque<Task>>(Collections.reverseOrder()))
        val deque2 = spyk<Deque<Task>>(LinkedList())
        val task2 = mockk<Task>(relaxed = true)
        deque2.push(task2)
        active[2] = deque2
        val deque = spyk<Deque<Task>>(LinkedList())
        deque.push(task)
        active[1] = deque
        val deque3 = spyk<Deque<Task>>(LinkedList())
        val task3 = mockk<Task>(relaxed = true)
        deque3.push(task3)
        active[0] = deque3
        every { task2.isCompleted } returns true
        every { task.isCompleted } returns false
        every { task3.isCompleted } returns true
        every { component.active } returns active
        //When
        val result = system.purge(component)
        //Then
        verify { deque2.poll() }
        verify(exactly = 0) {
            deque.poll()
            deque3.poll()
        }
        assertEquals(deque, result)
    }

    @Test
    fun `Resend ignores no update`() {
        //Given
        every { component.needsUpdate } returns false
        val suspension: TaskSuspension<Unit> = mockk(relaxed = true)
        //When
        system.resend(component, suspension)
        //Then
        verify(exactly = 0) { component.needsUpdate = false }
    }

    @Test
    fun `Resend removes update and ignores not action resendable`() {
        //Given
        every { component.needsUpdate } returns true
        val suspension: TaskSuspension<Unit> = mockk(relaxed = true, moreInterfaces = *arrayOf(Resendable::class))
        //When
        system.resend(component, suspension)
        //Then
        verify { component.needsUpdate = false }
    }

    @Test
    fun `Resend resends action resendable`() {
        //Given
        every { component.needsUpdate } returns true
        val suspension: TaskSuspension<Unit> = mockk(relaxed = true, moreInterfaces = *arrayOf(Action::class, Resendable::class))
        every { (suspension as Action).perform(suspension) } answers {}
        //When
        system.resend(component, suspension)
        //Then
        verify { (suspension as Action).perform(suspension) }
    }

}