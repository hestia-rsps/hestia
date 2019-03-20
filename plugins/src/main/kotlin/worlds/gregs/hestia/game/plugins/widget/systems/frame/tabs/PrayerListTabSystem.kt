package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import worlds.gregs.hestia.game.Configs.CURSES
import worlds.gregs.hestia.game.Configs.PRAYER_POINTS
import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.PrayerListTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigGlobal
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings
import worlds.gregs.hestia.services.send

class PrayerListTabSystem : BaseFrame(PrayerListTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        val quickPrayers = false
        es.send(entityId, WidgetComponentSettings(getId(entityId), if (quickPrayers) 42 else 8, 0, 29, options = *intArrayOf(0)))
        es.send(entityId, Config(PRAYER_POINTS, 990))
        es.send(entityId, Config(CURSES, 0))
        es.send(entityId, ConfigGlobal(181, 0))//Setting quick prayers
//        es.send(entityId, ConfigGlobal(182, 0))//Using quick prayers
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            12 -> {//Show/Hide stat adjustments
            }
        }
    }

    companion object {
        private const val TAB_ID = 271
        private const val RESIZABLE_INDEX = 96
        private const val FIXED_INDEX = 210
    }
}