package worlds.gregs.hestia.game.plugins.widget.systems.tabs

import worlds.gregs.hestia.game.Configs.CURSES
import worlds.gregs.hestia.game.Configs.PRAYER_POINTS
import worlds.gregs.hestia.game.plugins.widget.components.tabs.PrayerListTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseTab
import worlds.gregs.hestia.network.out.Config
import worlds.gregs.hestia.network.out.GlobalConfig
import worlds.gregs.hestia.network.out.UnlockComponentOption
import worlds.gregs.hestia.services.send

class PrayerListTabSystem : BaseTab(PrayerListTab::class) {

    override var id = TAB_ID

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        val quickPrayers = false
        es.send(entityId, UnlockComponentOption(id, if(quickPrayers) 42 else 8, 0, 29, 0))
        es.send(entityId, Config(PRAYER_POINTS, 10))
        es.send(entityId, Config(CURSES, 0))
        es.send(entityId, GlobalConfig(181, 0))//Setting quick prayers
//        es.send(entityId, GlobalConfig(182, 0))//Using quick prayers
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
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