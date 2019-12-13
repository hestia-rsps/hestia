package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class TickDeferralSystemTest : GameTest(WorldConfigurationBuilder()) {

    lateinit var system: TickDeferralSystem

    @RelaxedMockK
    lateinit var scope: TaskScope

    @RelaxedMockK
    lateinit var tasks: Tasks

    @SpyK
    var queue = TaskQueue()

    @BeforeEach
    override fun setup() {
        system = TickDeferralSystem()
        super.setup()
        world.createEntity().edit().add(queue)
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Ticks set deferral and defers`() = runBlocking {
        //When
        scope.wait(5)
        //Then
        coVerifySequence {
            scope.deferral = TickDeferral(5)
            scope.defer()
        }

        confirmVerified(scope)
    }

    @Test
    fun `Non tick context's are ignored`() = runBlocking {
        //Given
        every { queue.peek() } returns scope
        //When
        tick()
        //Then
        verify { scope.deferral }//Only called once to check type
        confirmVerified(scope)
    }

    @Test
    fun `Tick is decreased`() = runBlocking {
        //Given
        val deferral = TickDeferral(2)
        every { scope.deferral } returns deferral
        every { queue.peek() } returns scope
        //When
        tick()
        //Then
        coVerifySequence {
            scope.deferral
        }
        assertEquals(1, deferral.ticks)
        confirmVerified(scope)
    }

    @Test
    fun `Zero ticks remaining calls resume`() = runBlocking {
        //Given
        val deferral = TickDeferral(1)
        every { scope.deferral } returns deferral
        every { queue.peek() } returns scope
        //When
        tick()
        //Then
        assertEquals(0, deferral.ticks)
        coVerifyOrder {
            scope.deferral
            tasks.resume(0)
        }
        confirmVerified(scope)
    }
}