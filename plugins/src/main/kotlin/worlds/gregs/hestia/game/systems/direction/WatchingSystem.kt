package worlds.gregs.hestia.game.systems.direction

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.component.update.direction.Watch
import worlds.gregs.hestia.game.component.update.direction.Watching
import worlds.gregs.hestia.services.Aspect

/**
 * WatchingSystem
 * Change direction of entity to watch another entity
 */
class WatchingSystem : IteratingSystem(Aspect.all(Watch::class)) {
    private lateinit var watchMapper: ComponentMapper<Watch>
    private lateinit var watchingMapper: ComponentMapper<Watching>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var playerMapper: ComponentMapper<Player>

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
                    if (!world.entityManager.isActive(watch.entity)) {//TODO check is same as not null?
                        -1
                    } else {
                        (clientIndexMapper.get(watch.entity)?.index ?: 0) + if(playerMapper.has(watch.entity)) 32768 else 0
                    }
                }
        //Remove request
        watchMapper.remove(entityId)
    }
}