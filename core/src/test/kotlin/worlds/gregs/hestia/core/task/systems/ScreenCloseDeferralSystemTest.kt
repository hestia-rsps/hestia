package worlds.gregs.hestia.core.task.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.display.widget.model.events.ScreenClosed
import worlds.gregs.hestia.game.MockkGameTest
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
internal class ScreenCloseDeferralSystemTest : MockkGameTest(WorldConfigurationBuilder()) {

    @SpyK
    var system = ScreenCloseDeferralSystem()

    @SpyK
    var component = TaskQueue()

    @RelaxedMockK
    lateinit var scope: TaskScope

    @RelaxedMockK
    lateinit var queue: Tasks

    @RelaxedMockK
    lateinit var screen: KClass<ScreenWidget>

    @RelaxedMockK
    lateinit var widget: ScreenWidget

    lateinit var entity: Entity

    @BeforeEach
    override fun setup() {
        super.setup()
        entity = world.createEntity()
        entity.edit().add(component)
        tick()
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, queue)
    }

    @Test
    fun `Wait-for-screen sets deferral and defers`() = runBlocking {
        //When
        scope.waitForScreen(screen)
        //Then
        coVerifySequence {
            scope.deferral = ScreenClose(screen)
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Deferral ignored if not a screen close`() {
        //Given
        val deferral: DeferralType = mockk()
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        tick()
        //Then
        verify(exactly = 0) { queue.resume(0) }
    }

    @Test
    fun `Deferral resumed if screen is null`() {
        //Given
        val entityId = 0
        val deferral = ScreenClose(null)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        es.dispatch(ScreenClosed(entityId, widget))
        //Then
        verify { queue.resume(entityId) }
    }

    @Test
    fun `Deferral resumed if closed screen matches`() {
        //Given
        val entityId = 0
        val deferral = ScreenClose(screen)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        every { screen.isInstance(widget) } returns true
        //When
        es.dispatch(ScreenClosed(entityId, widget))
        //Then
        verify { queue.resume(entityId) }
    }

    @Test
    fun `Deferral ignored if screens don't match`() {
        //Given
        val entityId = 0
        val deferral = ScreenClose(screen)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        every { screen.isInstance(widget) } returns false
        //When
        es.dispatch(ScreenClosed(entityId, widget))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }

    @Test
    fun `Deferral process ignored if deferral not a screen open`() {
        //Given
        val entityId = 0
        val deferral: DeferralType = mockk()
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }

    @Test
    fun `Deferral skipped if screen is null and no screen open`() {
        //Given
        val entityId = 0
        val deferral = ScreenClose(null)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { queue.resume(entityId) }
    }

    @Test
    fun `Deferral skipped if screen set but not open`() {
        //Given
        val entityId = 0
        val deferral = ScreenClose(widget::class)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { queue.resume(entityId) }
    }

    @Test
    fun `Deferral not skipped if screen set and open`() {
        //Given
        entity.edit().add(widget)
        val entityId = 0
        val deferral = ScreenClose(widget::class)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }

    @Test
    fun `Deferral not skipped if screen null and any screen is open`() {
        //Given
        entity.edit().add(widget)
        val entityId = 0
        val deferral = ScreenClose(null)
        every { component.peek() } returns scope
        every { scope.deferral } returns deferral
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }
}