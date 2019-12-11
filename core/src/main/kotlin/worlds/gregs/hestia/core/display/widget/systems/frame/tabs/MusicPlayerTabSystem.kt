package worlds.gregs.hestia.core.display.widget.systems.frame.tabs

import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.MusicPlayerTab
import worlds.gregs.hestia.core.display.widget.systems.BaseFrame

class MusicPlayerTabSystem : BaseFrame(MusicPlayerTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 187
        private const val RESIZABLE_INDEX = 104
        private const val FIXED_INDEX = 218
    }
}