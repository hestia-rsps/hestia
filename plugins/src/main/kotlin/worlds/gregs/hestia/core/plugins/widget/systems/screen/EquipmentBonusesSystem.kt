package worlds.gregs.hestia.core.plugins.widget.systems.screen

import worlds.gregs.hestia.core.plugins.widget.components.screen.EquipmentBonuses
import worlds.gregs.hestia.core.plugins.widget.systems.BaseScreen

class EquipmentBonusesSystem : BaseScreen(EquipmentBonuses::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val ID = 667
    }
}