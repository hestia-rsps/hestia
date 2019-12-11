package worlds.gregs.hestia.core.plugins.widget.systems.frame.chat

import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.ChatBox
import worlds.gregs.hestia.core.plugins.widget.systems.BaseFrame

class ChatBoxSystem : BaseFrame(ChatBox::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 752
        private const val RESIZABLE_INDEX = 73
        private const val FIXED_INDEX = 192
    }
}