package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.`object`.model.components.ObjectType
import worlds.gregs.hestia.core.entity.`object`.model.components.Rotation
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.EntityStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.FixedTileStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.FloorItemStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.ObjectStrategy
import worlds.gregs.hestia.core.world.movement.model.components.calc.Path
import worlds.gregs.hestia.core.world.movement.model.components.calc.Route
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

/**
 * StrategySystem
 * Calculates the [RouteStrategy] to use in [PathSystem]
 */
@Wire(failOnNull = false, injectInherited = true)
class StrategySystem : IteratingSystem(Aspect.all(Position::class, Route::class)) {

    private lateinit var routeMapper: ComponentMapper<Route>
    private lateinit var objectMapper: ComponentMapper<GameObject>
    private lateinit var objectTypeMapper: ComponentMapper<ObjectType>
    private lateinit var rotationMapper: ComponentMapper<Rotation>
    private lateinit var playerMapper: ComponentMapper<Player>
    private lateinit var mobMapper: ComponentMapper<Mob>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private lateinit var objectDefinitions: ObjectDefinitionSystem
    private lateinit var publicMapper: ComponentMapper<Public>
    private lateinit var privateMapper: ComponentMapper<Private>

    override fun process(entityId: Int) {
        val route = routeMapper.get(entityId)
        routeMapper.remove(entityId)

        val targetId = route.entityId

        val targetPosition = positionMapper.get(targetId)

        //Choose strategy
        val strategy = when {
            playerMapper.has(targetId) || mobMapper.has(targetId) ->
                EntityStrategy(targetPosition.x, targetPosition.y, sizeMapper.width(targetId), sizeMapper.height(targetId), 0)
            objectMapper.has(targetId) -> {
                val id = objectMapper.get(targetId)
                val type = objectTypeMapper.get(targetId)
                val def = objectDefinitions.get(id.id)
                ObjectStrategy(type.type, targetPosition, rotationMapper.get(targetId).rotation, def.sizeX, def.sizeY, def.plane)
            }
            publicMapper.has(targetId) || privateMapper.has(targetId) -> FloorItemStrategy(targetPosition.x, targetPosition.y)
            else -> FixedTileStrategy(targetPosition.x, targetPosition.y)
        }

        //Navigate
        pathMapper.create(entityId).apply {
            this.strategy = strategy
        }
    }

}