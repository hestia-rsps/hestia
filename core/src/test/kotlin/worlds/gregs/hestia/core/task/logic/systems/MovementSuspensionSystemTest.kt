package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps

@ExtendWith(MockKExtension::class)
internal class MovementSuspensionSystemTest : MockkGame(WorldConfigurationBuilder()) {

    @SpyK
    var system = MovementSuspensionSystem()

    @SpyK
    var component = TaskQueue()

    @SpyK
    var shift = Shift()

    @SpyK
    var position = Position()

    @RelaxedMockK
    lateinit var tasks: Tasks

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component).add(Mobile()).add(position).add(shift)
        tick()
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Non-movement suspend ignored`() {
        //Given
        val entityId = 0
        val suspension: TaskType<*> = mockk()
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify(exactly = 0) {
            tasks.resume(entityId, suspension, any())
        }
    }

    @Test
    fun `Resume if no steps component`() {
        //Given
        val entityId = 0
        val suspension: RouteSuspension = mockk(relaxed = true)
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify {
            tasks.resume(entityId, suspension, any())
        }
    }

    @Test
    fun `Resume if no steps`() {
        //Given
        val entityId = 0
        val steps = spyk(Steps())
        world.getEntity(entityId).edit().add(steps)
        val suspension: RouteSuspension = mockk(relaxed = true)
        every { steps.hasNext } returns false
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `Don't resume if no steps`() {
        //Given
        val entityId = 0
        val steps = spyk(Steps())
        world.getEntity(entityId).edit().add(steps)
        val suspension: RouteSuspension = mockk(relaxed = true)
        every { steps.hasNext } returns true
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }
}