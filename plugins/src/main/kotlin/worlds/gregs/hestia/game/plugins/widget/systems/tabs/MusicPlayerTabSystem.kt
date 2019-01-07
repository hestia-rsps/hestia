package worlds.gregs.hestia.game.plugins.widget.systems.tabs

import worlds.gregs.hestia.game.plugins.widget.components.tabs.MusicPlayerTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseTab

class MusicPlayerTabSystem : BaseTab(MusicPlayerTab::class) {

    override var id = TAB_ID

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 187
        private const val RESIZABLE_INDEX = 104
        private const val FIXED_INDEX = 218
    }
}