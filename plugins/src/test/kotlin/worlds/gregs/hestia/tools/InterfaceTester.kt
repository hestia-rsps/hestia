package worlds.gregs.hestia.tools

import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.*
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.game.GameTest
import kotlin.reflect.KClass

abstract class InterfaceTester(config: WorldConfigurationBuilder) : GameTest(config) {

    internal open val ui = mock<UserInterface>()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(ui)
    }

    /**
     * Mocks [UserInterface] to think an interface is open
     * @param widget The interface to "open"
     */
    internal fun open(widget: KClass<out ScreenWidget>) {
        whenever(ui.contains(any(), eq(widget))).thenReturn(true)
    }
    /**
     * Mocks [UserInterface] to think an interface is open
     * @param widget The interface to "close"
     */
    internal fun close(widget: KClass<out ScreenWidget>) {
        whenever(ui.contains(any(), eq(widget))).thenReturn(false)
    }


    internal fun assertReloaded(times: Int, entity: Int) {
        verify(ui, times(times)).reload(entity)
    }


    internal fun assertOpened(times: Int, entity: Int, widget: ScreenWidget) {
        verify(ui, times(times)).open(entity, widget)
    }

    internal fun assertClosed(times: Int, entity: Int) {
        verify(ui, times(times)).close(entity)
    }

    internal fun assertClicked(times: Int, entity: Int, widget: Int, component: Int, option: Int) {
        verify(ui, times(times)).click(entity, widget, component, option)
    }

}