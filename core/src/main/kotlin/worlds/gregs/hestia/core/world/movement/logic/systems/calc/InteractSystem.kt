package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
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
import worlds.gregs.hestia.core.world.movement.model.events.Interact
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

/**
 * InteractSystem
 * Calculates the [RouteStrategy] to use in [PathSystem]
 */
class InteractSystem : PassiveSystem() {

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

    @Subscribe
    fun startRoute(action: Interact) {
        val (targetId, alternative) = action
        val targetPosition = positionMapper.get(targetId)

        //Choose strategy
        val strategy = when {
            playerMapper.has(targetId) || mobMapper.has(targetId) ->
                EntityStrategy(targetPosition.x, targetPosition.y, sizeMapper.width(targetId), sizeMapper.height(targetId), 0)
            objectMapper.has(targetId) -> {
                val id = objectMapper.get(targetId)
                val type = objectTypeMapper.get(targetId)
                val def = objectDefinitions.get(id.id)
                ObjectStrategy(type.type, targetPosition, rotationMapper.get(targetId).rotation, def.sizeX, def.sizeY, def.blockFlag)
            }
            publicMapper.has(targetId) || privateMapper.has(targetId) -> FloorItemStrategy(targetPosition.x, targetPosition.y)
            else -> FixedTileStrategy(targetPosition.x, targetPosition.y)
        }

        //Navigate
        pathMapper.create(action.entity).apply {
            this.strategy = strategy
            this.alternative = alternative
        }
    }

}