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
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceClosed
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.WindowClose
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

@ExtendWith(MockKExtension::class)
internal class WindowCloseSystemTest : MockkGame() {

    @SpyK
    var system = WindowCloseSystem()

    @RelaxedMockK
    lateinit var tasks: Tasks

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, interfaces)
    }

    @Test
    fun `Resume if windows match`() {
        //Given
        val entityId = 0
        val suspension: WindowClose = mockk(relaxed = true)
        every { suspension.window } returns Window.MAIN_SCREEN
        every { interfaces.getWindow(any()) } returns Window.MAIN_SCREEN
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.perform(entityId, InterfaceClosed(-1))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Skip if windows don't match`() {
        //Given
        val entityId = 0
        val suspension: WindowClose = mockk(relaxed = true)
        every { suspension.window } returns Window.FULL_SCREEN
        every { interfaces.getWindow(any()) } returns Window.MAIN_SCREEN
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.perform(entityId, InterfaceClosed(-1))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Resume if window isn't open`() {
        //Given
        val suspension: WindowClose = mockk(relaxed = true)
        val entityId = 0
        every { interfaces.hasInterface(entityId, any<Window>()) } returns false
        //When
        es.perform(entityId, ProcessTaskSuspension(suspension))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Skip if window is open`() {
        //Given
        val suspension: WindowClose = mockk(relaxed = true)
        val entityId = 0
        every { interfaces.hasInterface(entityId, any<Window>()) } returns true
        //When
        es.perform(entityId, ProcessTaskSuspension(suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, Unit) }
    }
}