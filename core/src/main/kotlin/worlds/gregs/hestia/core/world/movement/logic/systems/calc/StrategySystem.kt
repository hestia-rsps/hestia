package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.EntityStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.FixedTileStrategy
import worlds.gregs.hestia.core.world.movement.logic.strategies.ObjectStrategy
import worlds.gregs.hestia.core.world.movement.model.components.calc.Path
import worlds.gregs.hestia.core.world.movement.model.components.calc.Route
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.artemis.Aspect

/**
 * StrategySystem
 * Calculates the [RouteStrategy] to use in [PathSystem]
 */
@Wire(failOnNull = false, injectInherited = true)
class StrategySystem : IteratingSystem(Aspect.all(Position::class, Route::class)) {

    private lateinit var routeMapper: ComponentMapper<Route>
    private lateinit var objectMapper: ComponentMapper<GameObject>
    private lateinit var playerMapper: ComponentMapper<Player>
    private lateinit var mobMapper: ComponentMapper<Mob>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var pathMapper: ComponentMapper<Path>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private lateinit var es: EventSystem

    private val reach: (Int) -> Unit = {
        println("You can't reach that.")
//        messageMapper.create(it).message = "You can't reach that."
//        es.send(it, ResetMinimapFlag())
    }

    override fun process(entityId: Int) {
        val route = routeMapper.get(entityId)
        routeMapper.remove(entityId)

        val targetId = route.entityId

        //Check target entity exists
        if(!world.entityManager.isActive(targetId) || !positionMapper.has(targetId)) {
            route.failure?.invoke() ?: reach.invoke(entityId)
            return
        }

        val position = positionMapper.get(entityId)
        val targetPosition = positionMapper.get(targetId)

        //Check target is on same plane
        if(position.plane != targetPosition.plane) {
            route.failure?.invoke() ?: reach.invoke(entityId)
            return
        }

        //Choose strategy
        val strategy = when {
            playerMapper.has(targetId) || mobMapper.has(targetId) -> {
                EntityStrategy(targetPosition.x, targetPosition.y, sizeMapper.width(targetId), sizeMapper.height(targetId), 0)
            }
            objectMapper.has(targetId) -> ObjectStrategy(targetPosition.x, targetPosition.y)
            //TODO ground item
            else -> FixedTileStrategy(targetPosition.x, targetPosition.y)
        }

        //Navigate
        pathMapper.create(entityId).apply {
            this.strategy = strategy
        }
    }

}