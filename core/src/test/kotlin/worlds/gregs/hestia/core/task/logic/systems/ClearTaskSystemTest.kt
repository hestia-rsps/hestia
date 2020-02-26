package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.ClearTasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue

@ExtendWith(MockKExtension::class)
internal class ClearTaskSystemTest : MockkGame() {

    @SpyK
    var system = ClearTaskSystem()

    @RelaxedMockK
    lateinit var tasks: Tasks

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, interfaces)
    }

    @Test
    fun `Clear tasks`() {
        //Given
        val entityId = 0
        //When
        es.perform(entityId, ClearTasks())
        //Then
        verify { tasks.cancelAll(entityId, TaskCancellation.Priority, -1) }
    }

    @Test
    fun `Clear tasks priority`() {
        //Given
        val entityId = 0
        //When
        es.perform(entityId, ClearTasks(0))
        //Then
        verify { tasks.cancelAll(entityId, TaskCancellation.Priority, 0) }
    }

    @Test
    fun `Clear tasks cause`() {
        //Given
        val entityId = 0
        val cause = TaskCancellation.Cancellation("unit test")
        //When
        es.perform(entityId, ClearTasks(cause = cause))
        //Then
        verify { tasks.cancelAll(entityId, cause, -1) }
    }

    @Test
    fun `Task resumed tick after`() {
        //Given
        val entityId = 0
        world.createEntity().edit().add(TaskQueue())
        es.perform(entityId, ClearTasks())
        val suspension: ClearTasks = mockk(relaxed = true)
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }
}