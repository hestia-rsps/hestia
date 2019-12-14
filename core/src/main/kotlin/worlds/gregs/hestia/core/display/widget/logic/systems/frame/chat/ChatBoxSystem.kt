package worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat

import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.ChatBox
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame

class ChatBoxSystem : BaseFrame(ChatBox::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 752
        private const val RESIZABLE_INDEX = 73
        private const val FIXED_INDEX = 192
    }
}