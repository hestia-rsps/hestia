package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.ActionContext
import worlds.gregs.hestia.core.action.logic.systems.FloorItemOptionSystem
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.events.StartTask
import worlds.gregs.hestia.core.world.map.model.Chunk.toChunkPosition
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.FloorItemOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class FloorItemOptionHandler : MessageHandlerSystem<FloorItemOptionMessage>(), InteractionHandler {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var floorItems: FloorItems
    private lateinit var itemDefinitions: ItemDefinitionSystem
    private val logger = LoggerFactory.getLogger(FloorItemOptionHandler::class.java)!!
    private lateinit var options: FloorItemOptionSystem
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>
    override lateinit var stepsMapper: ComponentMapper<Steps>
    override lateinit var followingMapper: ComponentMapper<Following>
    override lateinit var shiftMapper: ComponentMapper<Shift>
    override lateinit var es: EventSystem

    override fun handle(entityId: Int, message: FloorItemOptionMessage) {
        val (id, run, y, x, optionId) = message
        val position = positionMapper.get(entityId)

        val chunk = toChunkPosition(x shr 3, y shr 3, position.plane)
        val items = floorItems.getItems(chunk)
        val item = items.firstOrNull { itemId ->
            positionMapper.get(itemId).same(x, y, position.plane)//Same tile
                    && typeMapper.get(itemId).id == id//Same type
        }
        if(item != null) {
            val definition = itemDefinitions.get(id)
            val optionString = definition.floorOptions.getOrNull(optionId - 1)
            if(optionString != null) {
                val action = options.get(optionString, id) ?: return logger.warn("Invalid floor item action $id $optionString")
                es.perform(entityId, StartTask(InactiveTask(1, interact(entityId, item, false) {
                    val actionContext = actionContextMapper.get(entityId)
                    action.invoke(actionContext, item)
                })))
            } else {
                logger.warn("Invalid floor item option $id $optionId")
            }
        } else {
            logger.warn("Invalid floor item $id $x $y")
        }
    }
}