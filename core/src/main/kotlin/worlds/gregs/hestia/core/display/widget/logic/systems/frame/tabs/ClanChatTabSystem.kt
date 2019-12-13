package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.ClanChatTab
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame

class ClanChatTabSystem : BaseFrame(ClanChatTab::class) {
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 1110
        private const val RESIZABLE_INDEX = 101
        private const val FIXED_INDEX = 215
    }
}