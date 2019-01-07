package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.ItemsKeptOnDeath
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class ItemsKeptOnDeathSystem : BaseScreen(ItemsKeptOnDeath::class) {
    override var id = 17

    override fun click(entityId: Int, componentId: Int, option: Int) {
        when(componentId) {
            28 -> {//What if I enter the wilderness?
            }
        }
    }
}