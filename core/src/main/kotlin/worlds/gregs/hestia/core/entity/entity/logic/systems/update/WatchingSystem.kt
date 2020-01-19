package worlds.gregs.hestia.core.entity.entity.logic.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.update.model.components.direction.Watch
import worlds.gregs.hestia.core.display.update.model.components.direction.Watching
import worlds.gregs.hestia.game.entity.Player

/**
 * WatchingSystem
 * Change direction of entity to watch another entity
 */
class WatchingSystem : PassiveSystem() {
    private lateinit var watchingMapper: ComponentMapper<Watching>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var playerMapper: ComponentMapper<Player>//TODO remove player dependency

    @Subscribe
    fun watch(event: Watch) {
        val entityId = event.entity
        val target = event.target
        //Set target to watch
        val watching = watchingMapper.create(entityId)

        //Convert to client index
        watching.clientIndex =
                //Attempted to watch self
                if (target < 0 || entityId == target) {
                    -1
                } else {
                    //Target doesn't exist
                    if (!world.entityManager.isActive(target)) {
                        -1
                    } else {
                        (clientIndexMapper.get(target)?.index ?: 0) + if (playerMapper.has(target)) 32768 else 0
                    }
                }
    }
}