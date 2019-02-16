package worlds.gregs.hestia.game.update.sync

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.components.ViewDistance
import worlds.gregs.hestia.network.update.sync.player.HeightPlayerSync
import worlds.gregs.hestia.network.update.sync.player.MoveGlobalPlayerSync
import worlds.gregs.hestia.network.update.sync.player.RegionPlayerSync

/**
 * A passive system used to create [SyncStage]s
 * @param local Whether factory creates local stages
 * @param mob Whether factory creates mob stages
 */
abstract class SyncFactory<T : SyncStage>(val local: Boolean, val mob: Boolean) : PassiveSystem() {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewDistanceMapper: ComponentMapper<ViewDistance>

    /**
     * Checks whether the [other] has changed within view of [main]
     * @param sync The synchronization class
     * @param main The main entity id
     * @param other The other entities id
     * @return Whether a change has occurred
     */
    abstract fun change(sync: Synchronize, main: Int, other: Int): Boolean

    /**
     * Creates an instance of [SyncStage]
     * @param main The main entity id
     * @param other The other entities id
     * @param update Whether a block update is needed
     * @return A sync stage
     */
    abstract fun create(main: Int, other: Int, update: Boolean): T

    /**
     * Creates a movement sync stage based on how much [other] has moved since last update
     * @param main The main entity id
     * @param other The entity to check that has been moved
     * @return Height, Region or Move SyncStage
     */
    internal fun createMovement(main: Int, other: Int): SyncStage? {
        if (!positionMapper.has(other)) {
            return null
        }
        val viewport = viewportMapper.get(main)
        val position = positionMapper.get(other)
        val lastPosition = viewport.getPosition(other)

        Position.regionDelta(position, lastPosition) { deltaX, deltaY, deltaPlane ->
            return if (deltaX == 0 && deltaY == 0 && deltaPlane == 0) {
                null//No update needed
            } else if (deltaX == 0 && deltaY == 0 && deltaPlane != 0) {
                HeightPlayerSync(position, lastPosition)//Change player plane
            } else if (deltaX == -1 || deltaY == -1 || deltaX == 1 || deltaY == 1) {
                RegionPlayerSync(position, lastPosition)//Move to adjacent region
            } else {
                MoveGlobalPlayerSync(position, lastPosition)//Move to a non-adjacent region
            }
        }

        //Can't get here
        return null
    }

    /**
     * Checks if [other] is within visible distance of [entity]
     * @param entity The entity who's looking
     * @param other The entity that's being looked for
     * @return Whether entity is within viewable distance
     */
    internal fun withinDistance(entity: Int, other: Int): Boolean {
        if(!positionMapper.has(entity) || !positionMapper.has(other)) {
            return false
        }
        val position = positionMapper.get(entity)
        val otherPosition = positionMapper.get(other)
        return otherPosition.withinDistance(position, viewDistanceMapper.get(entity)?.distance ?: DEFAULT_VIEW_DISTANCE)
    }

    companion object {
        const val DEFAULT_VIEW_DISTANCE = 15
    }
}