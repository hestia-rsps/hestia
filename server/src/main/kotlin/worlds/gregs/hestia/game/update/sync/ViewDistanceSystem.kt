package worlds.gregs.hestia.game.update.sync

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.api.mob.MobChunk
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.api.player.PlayerChunk
import worlds.gregs.hestia.game.update.components.ViewDistance
import worlds.gregs.hestia.game.update.sync.ViewDistanceSystem.Companion.MAXIMUM_LOCAL_ENTITIES
import worlds.gregs.hestia.services.Aspect

/**
 * Sets the [ViewDistance] to as high as possible without exceeding [MAXIMUM_LOCAL_ENTITIES]
 * Note: This system prioritises keeping a square viewport over displaying the maximum entities possible
 */
class ViewDistanceSystem : IteratingSystem(Aspect.all(Viewport::class, ViewDistance::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewDistanceMapper: ComponentMapper<ViewDistance>
    private var array = arrayOfNulls<Int>(DEFAULT_VIEW_DISTANCE)
    private lateinit var playerMapper: ComponentMapper<Player>
    private lateinit var mobChunk: MobChunk
    private lateinit var playerChunk: PlayerChunk

    override fun process(entityId: Int) {
        val viewDistance = viewDistanceMapper.get(entityId)

        //Reset
        for (index in 0 until array.size) {
            array[index] = null
        }

        val position = positionMapper.get(entityId)

        //For all entities
        (if(playerMapper.has(entityId)) playerChunk else mobChunk).get(position)
                .forEach {
                    //Calculate distance
                    val distance = position.getDistance(positionMapper.get(it))
                    if (distance < DEFAULT_VIEW_DISTANCE) {
                        array[distance] = (array[distance] ?: 0) + 1
                    }
                }

        var count = 0
        var distance = DEFAULT_VIEW_DISTANCE
        //Calculate the maximum possible view distance
        for (index in 0 until array.size) {
            count += array[index] ?: 0

            if (count > MAXIMUM_LOCAL_ENTITIES) {
                distance = index - 1
                break
            }
        }

        viewDistance.distance = distance
    }

    companion object {
        const val DEFAULT_VIEW_DISTANCE = 15
        const val MAXIMUM_LOCAL_ENTITIES = 255
    }
}
