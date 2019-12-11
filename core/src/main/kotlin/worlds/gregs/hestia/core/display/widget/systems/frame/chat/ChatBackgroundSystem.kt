package worlds.gregs.hestia.core.display.widget.systems.frame.chat

import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.ChatBackground
import worlds.gregs.hestia.core.display.widget.systems.BaseFrame

class ChatBackgroundSystem : BaseFrame(ChatBackground::class) {

    override fun getId(entityId: Int): Int {
        return FRAME_ID
    }

    override fun getWindow(entityId: Int): Int? {
        return WINDOW_ID
    }

    override fun getIndex(entityId: Int): Int {
        return INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {

    }

    companion object {
        private const val FRAME_ID = 137
        private const val WINDOW_ID = 752
        private const val INDEX = 9
    }

}