package worlds.gregs.hestia.core.display.widget.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.PriceChecker
import worlds.gregs.hestia.core.display.widget.systems.BaseScreen

class PriceCheckerSystem : BaseScreen(PriceChecker::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val ID = 206
    }
}