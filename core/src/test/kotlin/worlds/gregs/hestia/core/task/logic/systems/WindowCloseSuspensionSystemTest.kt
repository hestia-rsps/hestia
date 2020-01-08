package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.display.window.model.events.WindowClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class WindowCloseSuspensionSystemTest : MockkGame(WorldConfigurationBuilder()) {

    @SpyK
    var system = WindowPaneCloseSuspensionSystem()

    @SpyK
    var component = TaskQueue()

    @RelaxedMockK
    lateinit var task: Task

    @RelaxedMockK
    lateinit var tasks: Tasks

    var screen = 10

    var widget = screen

    lateinit var entity: Entity
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    override fun setup() {
        super.setup()
        entity = world.createEntity()
        entity.edit().add(component)
        tick()
        every { task.suspension = any() } propertyType WindowCloseSuspension::class answers {
            continuation = arg<WindowCloseSuspension>(0).continuation
            continuation.resume(Unit)
        }
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Wait-for-screen sets suspension and suspends`() = runBlocking {
        //When
        task.awaitWindow(screen)
        //Then
        coVerifySequence {
            task.suspension = WindowCloseSuspension(screen, continuation)
        }
        confirmVerified(task)
    }

    @Test
    fun `Suspend ignored if not a screen close`() {
        //Given
        val entityId = 0
        val suspension: TaskType<*> = mockk()
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        tick()
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }

    /*@Test
    fun `Suspend resumed if screen is null`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(null, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(WindowClosed(entityId, widget, false))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }*/

    @Test
    fun `Suspend resumed if closed screen matches`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(screen, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        every { screen == widget } returns true
        println(suspension)
        //When
        es.dispatch(WindowClosed(widget, false).apply { entity = entityId })
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Suspend ignored if screens don't match`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(screen, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        every { screen == widget } returns false
        //When
        es.dispatch(WindowClosed(widget, false).apply { entity = entityId })
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `Suspend process ignored if suspend not a screen open`() {
        //Given
        val entityId = 0
        val suspension: TaskType<*> = mockk()
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }

    /*@Test
    fun `Suspend skipped if screen is null and no screen open`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(null, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { tasks.resume(entityId, suspension, any()) }
    }*/

    @Test
    fun `Suspend skipped if screen set but not open`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(widget, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `Suspend not skipped if screen set and open`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(widget, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }

    /*@Test
    fun `Suspend not skipped if screen null and any screen is open`() {
        //Given
        val entityId = 0
        val suspension = WindowCloseSuspension(null, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }*/
}