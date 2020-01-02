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
import worlds.gregs.hestia.core.display.widget.model.components.ScreenWidget
import worlds.gregs.hestia.core.display.widget.model.events.ScreenClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import kotlin.coroutines.resume
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
internal class ScreenCloseSuspensionSystemTest : MockkGame(WorldConfigurationBuilder()) {

    @SpyK
    var system = ScreenCloseSuspensionSystem()

    @SpyK
    var component = TaskQueue()

    @RelaxedMockK
    lateinit var task: Task

    @RelaxedMockK
    lateinit var tasks: Tasks

    @RelaxedMockK
    lateinit var screen: KClass<ScreenWidget>

    @RelaxedMockK
    lateinit var widget: ScreenWidget

    lateinit var entity: Entity
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    override fun setup() {
        super.setup()
        entity = world.createEntity()
        entity.edit().add(component)
        tick()
        every { task.suspension = any() } propertyType ScreenCloseSuspension::class answers {
            continuation = arg<ScreenCloseSuspension>(0).continuation
            continuation.resume(Unit)
        }
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks)
    }

    @Test
    fun `Wait-for-screen sets suspension and suspends`() = runBlocking {
        //When
        task.awaitScreen(screen)
        //Then
        coVerifySequence {
            task.suspension = ScreenCloseSuspension(screen, continuation)
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

    @Test
    fun `Suspend resumed if screen is null`() {
        //Given
        val entityId = 0
        val suspension = ScreenCloseSuspension(null, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ScreenClosed(entityId, widget))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Suspend resumed if closed screen matches`() {
        //Given
        val entityId = 0
        val suspension = ScreenCloseSuspension(screen, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        every { screen.isInstance(widget) } returns true
        println(suspension)
        //When
        es.dispatch(ScreenClosed(entityId, widget))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Suspend ignored if screens don't match`() {
        //Given
        val entityId = 0
        val suspension = ScreenCloseSuspension(screen, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        every { screen.isInstance(widget) } returns false
        //When
        es.dispatch(ScreenClosed(entityId, widget))
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

    @Test
    fun `Suspend skipped if screen is null and no screen open`() {
        //Given
        val entityId = 0
        val suspension = ScreenCloseSuspension(null, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `Suspend skipped if screen set but not open`() {
        //Given
        val entityId = 0
        val suspension = ScreenCloseSuspension(widget::class, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `Suspend not skipped if screen set and open`() {
        //Given
        entity.edit().add(widget)
        val entityId = 0
        val suspension = ScreenCloseSuspension(widget::class, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }

    @Test
    fun `Suspend not skipped if screen null and any screen is open`() {
        //Given
        entity.edit().add(widget)
        val entityId = 0
        val suspension = ScreenCloseSuspension(null, mockk(relaxed = true))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, any()) }
    }
}