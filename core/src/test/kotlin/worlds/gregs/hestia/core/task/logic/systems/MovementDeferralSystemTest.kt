package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class MovementDeferralSystemTest : MockkGame(WorldConfigurationBuilder()) {

    @SpyK
    var system = MovementDeferralSystem()

    @SpyK
    var component = TaskQueue()

    @SpyK
    var shift = Shift()

    @SpyK
    var position = Position()

    @RelaxedMockK
    lateinit var scope: TaskScope

    @RelaxedMockK
    lateinit var queue: Tasks

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component).add(Mobile()).add(position).add(shift)
        tick()
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, queue)
    }

    @Test
    fun `Walked-to sets deferral and defers`() = runBlocking {
        //When
        scope.walkedTo(position)
        //Then
        coVerifySequence {
            scope.deferral = MovementDeferral(position)
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Non-movement deferral ignored`() {
        //Given
        val deferral: DeferralType = mockk()
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        tick()
        //Then
        verify(exactly = 0) {
            queue.resume(0)
        }
    }

    @Test
    fun `Matching movement processed`() {
        //Given
        val deferral = MovementDeferral(position)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        tick()
        //Then
        verify {
            position.same(position)
            queue.resume(0)
        }
    }

    @Test
    fun `Incorrect position movement not processed`() {
        //Given
        val deferral = MovementDeferral(position)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        every { position.same(position) } returns false
        //When
        tick()
        //Then
        verify { position.same(position) }
        verify(exactly = 0) { queue.resume(0) }
    }
}