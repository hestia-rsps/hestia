package worlds.gregs.hestia.game.plugins.entity.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.client.components.ClientIndex
import worlds.gregs.hestia.api.client.update.components.direction.Watch
import worlds.gregs.hestia.api.client.update.components.direction.Watching
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.services.Aspect

/**
 * WatchingSystem
 * Change direction of entity to watch another entity
 */
class WatchingSystem : IteratingSystem(Aspect.all(Watch::class)) {
    private lateinit var watchMapper: ComponentMapper<Watch>
    private lateinit var watchingMapper: ComponentMapper<Watching>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var playerMapper: ComponentMapper<Player>//TODO remove player dependency

    override fun process(entityId: Int) {
        val watch = watchMapper.get(entityId)
        //Set target to watch
        val watching = watchingMapper.create(entityId)

        //Convert to client index
        watching.clientIndex =
                //Attempted to watch self
                if (watch.entity < 0 || entityId == watch.entity) {
                    -1
                } else {
                    //Target doesn't exist
                    if (!world.entityManager.isActive(watch.entity)) {
                        -1
                    } else {
                        (clientIndexMapper.get(watch.entity)?.index ?: 0) + if(playerMapper.has(watch.entity)) 32768 else 0
                    }
                }
        //Remove request
        watchMapper.remove(entityId)
    }
}