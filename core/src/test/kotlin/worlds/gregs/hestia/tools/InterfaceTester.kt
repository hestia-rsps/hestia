package worlds.gregs.hestia.tools

import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.*
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.display.window.api.Windows

abstract class InterfaceTester(config: WorldConfigurationBuilder) : GameTest(config) {

    internal open val ui = mock<Windows>()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(ui)
    }

    /**
     * Mocks [UserInterface] to think an interface is open
     * @param widget The interface to "open"
     */
    internal fun open(widget: Int) {
        whenever(ui.hasWindow(any(), eq(widget))).thenReturn(true)
    }
    /**
     * Mocks [UserInterface] to think an interface is open
     * @param widget The interface to "close"
     */
    internal fun close(widget: Int) {
        whenever(ui.hasWindow(any(), eq(widget))).thenReturn(false)
    }


    internal fun assertReloaded(times: Int, entity: Int) {
        verify(ui, times(times)).refreshWindow(eq(entity), any())
    }


    internal fun assertOpened(times: Int, entity: Int, widget: Int) {
        verify(ui, times(times)).openWindow(eq(entity), eq(widget))
    }

    internal fun assertClosed(times: Int, entity: Int) {
        verify(ui, times(times)).closeWindow(eq(entity), any(), any())
    }

    internal fun assertClicked(times: Int, entity: Int, window: Int, widget: Int, option: Int) {
//        verify(ui, times(times)).click(entity, 0, window, widget, 0, 0, option)
    }

}