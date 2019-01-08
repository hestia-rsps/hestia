package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.InventoryTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.ComponentSettings
import worlds.gregs.hestia.network.game.out.SendItems
import worlds.gregs.hestia.services.send

class InventoryTabSystem : BaseFrame(InventoryTab::class) {

    override fun open(entityId: Int) {
        super.open(entityId)
        es.send(entityId, ComponentSettings(getId(entityId), 0, 0, 27, 4554126))
//        es.send(entityId, ComponentSettings(id, 0, 28, 55, 2097152))//TODO this is prob one of those special inventories, which one and is this necessary here?
        es.send(entityId, SendItems(93, (0 until 24).toList()))
    }

    override fun getId(entityId: Int): Int {
        return INV_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val INV_ID = 679
        private const val RESIZABLE_INDEX = 94
        private const val FIXED_INDEX = 208
    }
}