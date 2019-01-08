package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.NotesTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.ComponentSettings
import worlds.gregs.hestia.services.send

class NotesTabSystem : BaseFrame(NotesTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        es.send(entityId, ComponentSettings(getId(entityId), 9, 0, 30, 2621470))
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 34
        private const val RESIZABLE_INDEX = 105
        private const val FIXED_INDEX = 219
    }
}