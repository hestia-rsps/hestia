package worlds.gregs.hestia.core.display.widget.logic.systems

import worlds.gregs.hestia.core.display.widget.model.components.ScreenWidget
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