package worlds.gregs.hestia.core.display.widget.logic.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseScreen

class ItemsKeptOnDeathSystem : BaseScreen(ItemsKeptOnDeath::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        when(componentId) {
            28 -> {//What if I enter the wilderness?
            }
        }
    }

    companion object {
        private const val ID = 17
    }
}