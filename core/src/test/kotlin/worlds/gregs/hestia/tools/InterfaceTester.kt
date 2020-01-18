package worlds.gregs.hestia.tools

import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.*
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces

abstract class InterfaceTester(config: WorldConfigurationBuilder) : GameTest(config) {

    internal open val ui = mock<Interfaces>()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(ui)
    }

    /**
     * Mocks [UserInterface] to think an interface is open
     * @param id The interface to "open"
     */
    internal fun open(id: Int) {
        whenever(ui.hasInterface(any(), eq(id))).thenReturn(true)
    }
    /**
     * Mocks [UserInterface] to think an interface is open
     * @param id The interface to "close"
     */
    internal fun close(id: Int) {
        whenever(ui.hasInterface(any(), eq(id))).thenReturn(false)
    }


    internal fun assertReloaded(times: Int, entity: Int) {
        verify(ui, times(times)).refreshInterface(eq(entity), any())
    }


    internal fun assertOpened(times: Int, entity: Int, id: Int) {
        verify(ui, times(times)).openInterface(eq(entity), eq(id))
    }

    internal fun assertClosed(times: Int, entity: Int) {
        verify(ui, times(times)).closeInterface(eq(entity), any(), any())
    }

    internal fun assertClicked(times: Int, entity: Int, id: Int, component: Int, option: Int) {
//        verify(ui, times(times)).click(entity, 0, id, component, 0, 0, option)
    }

}