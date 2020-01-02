package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.events.FloorItemOption
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.FloorItemOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class FloorItemOptionHandler : MessageHandlerSystem<FloorItemOptionMessage>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var floorItems: FloorItems
    private lateinit var es: EventSystem
    private lateinit var itemDefinitions: ItemDefinitionSystem
    private val logger = LoggerFactory.getLogger(FloorItemOptionHandler::class.java)!!

    override fun handle(entityId: Int, message: FloorItemOptionMessage) {
        val (id, run, y, x, option) = message
        val position = positionMapper.get(entityId)

        val chunk = toChunkPosition(x shr 3, y shr 3, position.plane)
        val items = floorItems.getItems(chunk)
        val item = items.firstOrNull { itemId ->
            positionMapper.get(itemId).same(x, y, position.plane)//Same tile
                    && typeMapper.get(itemId).id == id//Same type
        }

        if(item != null) {
            val definition = itemDefinitions.get(id)
            val action = definition.floorOptions.getOrNull(option - 1)
            if(action != null) {
                es.dispatch(FloorItemOption(entityId, item, action))
            } else {
                logger.warn("Invalid floor item option $id $option")
            }
        } else {
            logger.warn("Invalid floor item $id $x $y")
        }
    }
}