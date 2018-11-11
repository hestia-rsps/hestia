package world.gregs.hestia.game.systems.direction

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.component.update.direction.Watch
import world.gregs.hestia.game.component.update.direction.Watching
import world.gregs.hestia.services.Aspect

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
                if (entityId == watch.entity) {
                    -1
                } else {

                    //Get target entity
                    val entity = world.getEntity(watch.entity)

                    //Target doesn't exist
                    if (entity == null) {
                        -1
                    } else {
                        (clientIndexMapper.get(entityId)?.index ?: 0) + if(playerMapper.has(entityId)) 32768 else 0
                    }
                }
        //Remove request
        watchMapper.remove(entityId)
    }
}