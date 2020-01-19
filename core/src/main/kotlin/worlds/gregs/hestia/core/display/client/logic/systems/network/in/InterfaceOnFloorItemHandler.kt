package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.events.ItemOnFloorItem
import worlds.gregs.hestia.core.world.map.model.Chunk
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnFloorItem

class InterfaceOnFloorItemHandler : MessageHandlerSystem<InterfaceOnFloorItem>() {

    private lateinit var es: EventSystem

    private lateinit var inventoryMapper: ComponentMapper<Inventory>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var floorItems: FloorItems
    private lateinit var interfaces: Interfaces
    private val logger = LoggerFactory.getLogger(InterfaceOnFloorItemHandler::class.java)!!

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceOnFloorItem) {
        val (x, y, floorType, hash, slot, _, type) = message
        val inventory = inventoryMapper.get(entityId) ?: return logger.warn("Unhandled component on floor item $message")

        if(!interfaces.hasInterface(entityId, hash)) {
            return logger.warn("Invalid component on floor item hash $message")
        }

        val inventoryItem = inventory.items.getOrNull(slot)

        if(inventoryItem == null || inventoryItem.type != type) {
            return logger.warn("Invalid component on floor item message $message")
        }

        //Find floor item
        val position = positionMapper.get(entityId)
        val chunk = Chunk.toChunkPosition(x shr 3, y shr 3, position.plane)
        val items = floorItems.getItems(chunk)
        val floorItem = items.firstOrNull { itemId ->
            positionMapper.get(itemId).same(x, y, position.plane)//Same tile
                    && typeMapper.get(itemId).id == floorType//Same type
        } ?: return logger.warn("Invalid component on floor item $message")

        es.perform(entityId, ItemOnFloorItem(floorItem, hash, slot, type))
    }
}