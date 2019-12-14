package worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat

import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.PrivateChat
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame

class PrivateChatSystem : BaseFrame(PrivateChat::class) {
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 754
        private const val RESIZABLE_INDEX = 72
        private const val FIXED_INDEX = 17
    }
}