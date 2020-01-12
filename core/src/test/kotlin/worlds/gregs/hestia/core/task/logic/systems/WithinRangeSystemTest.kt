package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.movement.api.Mobile

@ExtendWith(MockKExtension::class)
internal class WithinRangeSystemTest : MockkGame() {

    @SpyK
    var system = WithinRangeSystem()

    @SpyK
    var component = TaskQueue()

    @SpyK
    var position = Position(0, 0, 0)

    @RelaxedMockK
    lateinit var tasks: Tasks

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component).add(Mobile()).add(position)
        tick()
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Non-distance suspend ignored`() {
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
    fun `Invalid target id resumes false`() {
        //Given
        val entityId = 0
        val suspension = WithinRange(1, 1, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify { tasks.resume(entityId, suspension, false) }
    }

    @Test
    fun `Invalid target position resumes false`() {
        //Given
        val target = world.createEntity()
        target.edit().add(TaskQueue()).add(Mobile())
        tick()
        val entityId = 0
        val suspension = WithinRange(target.id, 1, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify { tasks.resume(entityId, suspension, false) }
    }

    @Test
    fun `Out of range target doesn't resume`() {
        //Given
        val target = world.createEntity()
        target.edit().add(TaskQueue()).add(Mobile()).add(Position(2, 0, 0))
        tick()
        val entityId = 0
        val suspension = WithinRange(target.id, 1, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `In range of target returns true`() {
        //Given
        val target = world.createEntity()
        target.edit().add(TaskQueue()).add(Mobile()).add(Position(1, 0, 0))
        tick()
        val entityId = 0
        val suspension = WithinRange(target.id, 1, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify { tasks.resume(entityId, suspension, true) }
    }
}