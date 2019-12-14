package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.task.model.Task
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.core.task.model.events.StartTask
import worlds.gregs.hestia.core.display.widget.model.events.CloseDialogue
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.game.task.DeferQueue
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskPriority
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class TaskSystemTest : MockkGame(WorldConfigurationBuilder()) {

    @SpyK
    var system = TaskSystem()

    @SpyK
    var component = TaskQueue()

    @RelaxedMockK
    lateinit var scope: TaskScope

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
    fun `Strong task clears queue on process`() {
        //Given
        val queue: DeferQueue = mockk()
        component.queue.add(scope)
        val task = Task(TaskPriority.Strong, queue)
        every { system.resume(0, any()) } answers { }
        every { system.clear(0) } answers { }
        //When
        es.dispatch(StartTask(0, task))
        //Then
        verify {
            system.clear(0)
        }
    }

    @Test
    fun `Other tasks don't clears queue on process`() {
        //Given
        val queue: DeferQueue = mockk()
        component.queue.add(scope)
        every { system.resume(0, any()) } answers { }
        every { system.clear(0) } answers { }
        //When
        es.dispatch(StartTask(0, Task(TaskPriority.Weak, queue)))
        es.dispatch(StartTask(0, Task(TaskPriority.Normal, queue)))
        //Then
        verify(exactly = 0) {
            system.clear(0)
        }
    }
    @Test
    fun `Resume uses first in queue`() {
        //Given
        val entityId = 0
        coEvery { system.resume(entityId, any()) } answers {}
        every { component.peek() } returns scope
        //When
        system.resume(entityId)
        //Then
        coVerifyOrder {
            component.peek()
            system.resume(entityId, scope)
        }
        confirmVerified(scope)
        confirmVerified(component)
    }

    @Test
    fun `Clear removes entries and dispatches event`() {
        //Given
        val entityId = 0
        component.queue.add(scope)
        every { component.clear() } answers { }
        //When
        system.clear(entityId)
        //Then
        verifyOrder {
            component.clear()
            es.dispatch(CloseDialogue(entityId))
        }
    }

    @Test
    fun `Resume ended coroutine is removed and event dispatched`() {
        //Given
        val entityId = 0
        coEvery { scope.next() } returns null
        every { scope.stopped() } returns true
        every { component.poll() } returns scope
        //When
        system.resume(entityId, scope)
        //Then
        coVerifyOrder {
            scope.next()
            scope.stopped()
            component.poll()
            scope.stop(false)
            es.dispatch(CloseDialogue(entityId))
        }
        confirmVerified(scope)
        confirmVerified(component)
    }

    @Test
    fun `Resume suspended coroutine is processed`() {
        //Given
        val entityId = 0
        val deferral: DeferralType = mockk()
        coEvery { scope.next() } returns deferral
        every { scope.stopped() } returns false
        //When
        system.resume(entityId, scope)
        //Then
        coVerifyOrder {
            scope.next()
            scope.stopped()
            es.dispatch(ProcessDeferral(entityId, deferral))
        }
        confirmVerified(scope)
    }
}