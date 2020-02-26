package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.toArray
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.floor.model.events.ItemOnObject
import worlds.gregs.hestia.core.world.land.model.components.LandObjects
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnObject

class InterfaceOnObjectHandler : MessageHandlerSystem<InterfaceOnObject>() {

    private lateinit var es: EventSystem

    private lateinit var containerSystem: ContainerSystem
    private val logger = LoggerFactory.getLogger(InterfaceOnObjectHandler::class.java)!!
    private lateinit var regionIdentifierMapper: ComponentMapper<RegionIdentifier>
    private lateinit var landObjectsMapper: ComponentMapper<LandObjects>
    private lateinit var gameObjectMapper: ComponentMapper<GameObject>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var regions: EntitySubscription
    private lateinit var interfaces: Interfaces

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
        regions = world.aspectSubscriptionManager.get(Aspect.all(RegionIdentifier::class, LandObjects::class))
    }

    override fun handle(entityId: Int, message: InterfaceOnObject) {
        val (_, y, slot, hash, type, x, id) = message

        if(!interfaces.hasInterface(entityId, hash)) {
            return logger.warn("Invalid interface on object hash $message")
        }

        val container = containerSystem.getContainer(entityId, hash shr 16) ?: return logger.warn("Unhandled interface on object $message")
        val item = container.getOrNull(slot)

        if(item == null || item.type != type) {
            return logger.warn("Invalid interface on object item $message")
        }

        //Find region
        val position = positionMapper.get(entityId)
        val objectPosition = Position.create(x, y, position.plane)
        val regionId = regions.entities.toArray().firstOrNull { regionIdentifierMapper.get(it).regionX == objectPosition.regionX && regionIdentifierMapper.get(it).regionY == objectPosition.regionY }
                ?: return logger.warn("Invalid interface on object region $message")

        //Find object
        val landObjects = landObjectsMapper.get(regionId)
        val pos = Position.hash18Bit(objectPosition.xInRegion, objectPosition.yInRegion, objectPosition.plane)
        val objectId = landObjects.list[pos]?.firstOrNull { gameObjectMapper.get(it).id == id }
                ?: return logger.warn("Invalid interface on object message $message")

        es.perform(entityId, ItemOnObject(objectId, hash, slot, type))
    }
}