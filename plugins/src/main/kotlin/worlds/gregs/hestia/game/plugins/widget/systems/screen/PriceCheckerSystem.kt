package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.PriceChecker
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class PriceCheckerSystem : BaseScreen(PriceChecker::class) {
    override var id = 206

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }
}