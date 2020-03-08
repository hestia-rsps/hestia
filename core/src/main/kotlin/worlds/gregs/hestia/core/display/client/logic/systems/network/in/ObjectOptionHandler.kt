package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import com.artemis.World
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.toArray
import worlds.gregs.hestia.core.action.model.ActionContext
import worlds.gregs.hestia.core.action.logic.systems.ObjectOptionSystem
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.land.model.components.LandObjects
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.ObjectOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

class ObjectOptionHandler : MessageHandlerSystem<ObjectOptionMessage>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var objectDefinitions: ObjectDefinitionSystem
    private val logger = LoggerFactory.getLogger(ObjectOptionHandler::class.java)!!
    private lateinit var regionIdentifierMapper: ComponentMapper<RegionIdentifier>
    private lateinit var landObjectsMapper: ComponentMapper<LandObjects>
    private lateinit var gameObjectMapper: ComponentMapper<GameObject>
    private lateinit var regions: EntitySubscription
    private lateinit var options: ObjectOptionSystem
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>

    override fun setWorld(world: World) {
        super.setWorld(world)
        regions = world.aspectSubscriptionManager.get(Aspect.all(RegionIdentifier::class, LandObjects::class))
    }

    override fun handle(entityId: Int, message: ObjectOptionMessage) {
        val (id, x, y, run, optionId) = message
        val position = positionMapper.get(entityId)
        val objectPosition = Position.create(x, y, position.plane)
        val regionId = regions.entities.toArray().firstOrNull { regionIdentifierMapper.get(it).regionX == objectPosition.regionX && regionIdentifierMapper.get(it).regionY == objectPosition.regionY }
        if(regionId != null) {
            val landObjects = landObjectsMapper.get(regionId)
            val pos = Position.hash18Bit(objectPosition.xInRegion, objectPosition.yInRegion, objectPosition.plane)
            val objectId = landObjects.list[pos]?.firstOrNull { gameObjectMapper.get(it).id == id }
            if(objectId != null) {
                val def = objectDefinitions.get(id)
                val optionString = def.options.getOrNull(optionId - 1)
                if(optionString != null) {
                    val action = options.get(optionString, id) ?: return logger.warn("Invalid object action $id $optionString")
                    val actionContext = actionContextMapper.get(entityId)
                    action.invoke(actionContext, objectId)
                } else {
                    logger.warn("Invalid object option $id $optionId ${def.options.toList()}")
                }
            } else {
                logger.warn("Can't find object $id $x $y")
            }
        } else {
            logger.warn("Can't find region $id $objectPosition")
        }
    }
}