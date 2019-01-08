package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import com.artemis.annotations.Wire
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.WornEquipmentTab
import worlds.gregs.hestia.game.plugins.widget.components.screen.EquipmentBonuses
import worlds.gregs.hestia.game.plugins.widget.components.screen.ItemsKeptOnDeath
import worlds.gregs.hestia.game.plugins.widget.components.screen.PriceChecker
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.SendItems
import worlds.gregs.hestia.services.send

@Wire(failOnNull = false, injectInherited = true)
class WornEquipmentTabSystem : BaseFrame(WornEquipmentTab::class) {

    private var ui: UserInterface? = null

    override fun getId(entityId: Int): Int {
        return EQUIP_TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_EQUIP_INDEX else FIXED_EQUIP_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        es.send(entityId, SendItems(94, (0 until 15).toList()))
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
        when(componentId) {
            8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38, 50 -> {
                val index = if(componentId == 50) 11 else (componentId - 8) / 3
                when(option) {
                    1 -> {}//Remove
                    8 -> {}//Examine
                }
            }
            39 -> {//Show equipment stats
                ui?.open(entityId, EquipmentBonuses())
            }
            42 -> {//Show price-checker
                ui?.open(entityId, PriceChecker())
            }
            45 -> {//Show items kept on death
                ui?.open(entityId, ItemsKeptOnDeath())
            }
        }
    }

    companion object {
        private const val EQUIP_TAB_ID = 387
        private const val RESIZABLE_EQUIP_INDEX = 95
        private const val FIXED_EQUIP_INDEX = 209
    }
}