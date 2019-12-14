package worlds.gregs.hestia.core.display.widget.logic.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.EquipmentBonuses
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseScreen

class EquipmentBonusesSystem : BaseScreen(EquipmentBonuses::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
    }

    companion object {
        private const val ID = 667
    }
}