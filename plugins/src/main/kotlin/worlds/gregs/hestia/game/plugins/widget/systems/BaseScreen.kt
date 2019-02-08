package worlds.gregs.hestia.game.plugins.widget.systems

import worlds.gregs.hestia.api.widget.components.ScreenWidget
import kotlin.reflect.KClass

abstract class BaseScreen(widget: KClass<out ScreenWidget>) : BaseWidget(widget) {

    override fun getIndex(resizable: Boolean): Int {
        return if (resizable) RESIZABLE_SCREEN_TAB_ID else FIXED_SCREEN_TAB_ID
    }

    companion object {
        private const val FIXED_SCREEN_TAB_ID = 9
        private const val RESIZABLE_SCREEN_TAB_ID = 12
    }
}