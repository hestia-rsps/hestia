package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.perform
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.item.container.model.events.ItemOnItem
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOnWidget

class WidgetOnWidgetHandler : MessageHandlerSystem<WidgetOnWidget>() {

    private lateinit var es: EventSystem

    private lateinit var inventoryMapper: ComponentMapper<Inventory>
    private val logger = LoggerFactory.getLogger(WidgetOnWidgetHandler::class.java)!!
    private lateinit var windows: Windows

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WidgetOnWidget) {
        val (fromHash, fromType, fromSlot, toHash, toType, toSlot) = message
        val inventory = inventoryMapper.get(entityId) ?: return logger.warn("Unhandled widget on widget $message")

        if(!windows.hasWindow(entityId, fromHash) || !windows.hasWindow(entityId, toHash)) {
            return logger.warn("Invalid widget on widget hash $message")
        }

        val fromItem = inventory.items.getOrNull(fromSlot)
        val toItem = inventory.items.getOrNull(toSlot)

        if(fromItem == null || fromItem.type != fromType || toItem == null || toItem.type != toType) {
            return logger.warn("Invalid widget on widget message $message")
        }

        es.perform(entityId, ItemOnItem(fromHash, fromType, fromSlot, toHash, toType, toSlot))
    }
}