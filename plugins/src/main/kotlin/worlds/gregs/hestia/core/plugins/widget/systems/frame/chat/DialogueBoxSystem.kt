package worlds.gregs.hestia.core.plugins.widget.systems.frame.chat

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.DialogueBox
import worlds.gregs.hestia.core.plugins.widget.systems.BaseFrame

class DialogueBoxSystem : BaseFrame(DialogueBox::class) {
    private lateinit var dialogueBoxMapper: ComponentMapper<DialogueBox>
    override fun getId(entityId: Int): Int {
        return if(dialogueBoxMapper.has(entityId)) {
            dialogueBoxMapper.get(entityId).id
        } else {
            0
        }
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
        private const val WINDOW_ID = 752
        private const val INDEX = 13
    }

    fun openChatInterface(entityId: Int, widgetId: Int) {
        val existed = dialogueBoxMapper.has(entityId)
        val dialogueBox = dialogueBoxMapper.create(entityId)
        dialogueBox.id = widgetId
        if(existed) {
            //Refresh
            open(entityId)
        }
    }

}