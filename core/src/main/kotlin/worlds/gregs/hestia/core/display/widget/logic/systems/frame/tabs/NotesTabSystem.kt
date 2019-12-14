package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.NotesTab
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings
import worlds.gregs.hestia.artemis.send

class NotesTabSystem : BaseFrame(NotesTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        es.send(entityId, WidgetComponentSettings(getId(entityId), 9, 0, 30, 2621470))
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 34
        private const val RESIZABLE_INDEX = 105
        private const val FIXED_INDEX = 219
    }
}