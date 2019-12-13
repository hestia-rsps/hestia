package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.display.widget.api.UserInterface
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.WornEquipmentTab
import worlds.gregs.hestia.core.display.widget.model.components.screen.EquipmentBonuses
import worlds.gregs.hestia.core.display.widget.model.components.screen.ItemsKeptOnDeath
import worlds.gregs.hestia.core.display.widget.model.components.screen.PriceChecker
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItems
import worlds.gregs.hestia.service.send

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
        es.send(entityId, WidgetItems(94, (0 until 15).toList()))
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
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