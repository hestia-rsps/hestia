package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.EquipmentBonuses
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class EquipmentBonusesSystem : BaseScreen(EquipmentBonuses::class) {
    override var id = 667

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }
}