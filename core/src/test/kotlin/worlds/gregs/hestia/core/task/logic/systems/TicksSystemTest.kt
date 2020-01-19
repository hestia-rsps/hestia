package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.CancellableContinuation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.api.Tickable
import worlds.gregs.hestia.core.task.model.components.TaskQueue

@ExtendWith(MockKExtension::class)
internal class TicksSystemTest : GameTest(WorldConfigurationBuilder()) {

    @SpyK
    var system = TicksSystem()

    @RelaxedMockK
    lateinit var tasks: Tasks

    @SpyK
    var queue = TaskQueue()

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(queue)
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Process ticks`() {
        //Given
        val entityId = 0
        val suspension = object : Tickable {
            override var ticks = 5
            override lateinit var continuation: CancellableContinuation<Unit>
        }
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        assertEquals(4, suspension.ticks)
        verify(exactly = 0) { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Process zero ticks resumes`() {
        //Given
        val entityId = 0
        val suspension = object : Tickable {
            override var ticks = 1
            override lateinit var continuation: CancellableContinuation<Unit>
        }
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        assertEquals(0, suspension.ticks)
        verify { tasks.resume(entityId, suspension, Unit) }
    }
}