package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class TickSuspensionSystemTest : GameTest(WorldConfigurationBuilder()) {

    @SpyK
    var system = TickSuspensionSystem()

    @RelaxedMockK
    lateinit var task: Task

    @RelaxedMockK
    lateinit var tasks: Tasks

    @SpyK
    var queue = TaskQueue()
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(queue)
        every { task.suspension = any() } propertyType TickSuspension::class answers {
            continuation = arg<TickSuspension>(0).continuation
            continuation.resume(Unit)
        }
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Ticks set suspension and suspends`() = runBlocking {
        //When
        task.wait(5)
        //Then
        coVerifySequence {
            task.suspension = TickSuspension(5, continuation)
        }

        confirmVerified(task)
    }

    @Test
    fun `Non tick context's are ignored`() = runBlocking {
        //Given
        val suspension: TaskType<Unit> = mockk(relaxed = true)
        every { tasks.getSuspension(0) } returns suspension
        //When
        tick()
        //Then
        verify(exactly = 0) { tasks.resume(0, suspension, Unit) }
        confirmVerified(task)
    }

    @Test
    fun `Tick is decreased`() = runBlocking {
        //Given
        val suspension = TickSuspension(2, mockk(relaxed = true))
        every { tasks.getSuspension(0) } returns suspension
        //When
        tick()
        //Then
        assertEquals(1, suspension.ticks)
    }

    @Test
    fun `Zero ticks remaining calls resume`() = runBlocking {
        //Given
        val suspension = TickSuspension(1, mockk(relaxed = true))
        every { tasks.getSuspension(0) } returns suspension
        //When
        tick()
        //Then
        assertEquals(0, suspension.ticks)
        verify {
            tasks.resume(0, suspension, any())
        }
        confirmVerified(task)
    }
}