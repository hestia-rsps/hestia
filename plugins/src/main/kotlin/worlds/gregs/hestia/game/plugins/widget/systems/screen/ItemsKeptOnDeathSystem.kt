package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.ItemsKeptOnDeath
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class ItemsKeptOnDeathSystem : BaseScreen(ItemsKeptOnDeath::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            28 -> {//What if I enter the wilderness?
            }
        }
    }

    companion object {
        private const val ID = 17
    }
}