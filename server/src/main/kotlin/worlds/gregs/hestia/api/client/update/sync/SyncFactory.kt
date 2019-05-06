package worlds.gregs.hestia.api.client.update.sync

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.network.update.sync.player.HeightPlayerSync
import worlds.gregs.hestia.network.update.sync.player.MoveGlobalPlayerSync
import worlds.gregs.hestia.network.update.sync.player.RegionPlayerSync

/**
 * A passive system used to create [SyncStage]s
 * @param local Whether factory creates local stages
 * @param mob Whether factory creates mob stages
 */
abstract class SyncFactory<T : SyncStage>(open val local: Boolean, open val mob: Boolean, open val active: Boolean) : PassiveSystem() {

    /**
     * Checks whether the [other] has changed within view of [bag]
     * @param sync The synchronization class
     * @param other The other entities id
     * @param bag The local sync bag
     * @return Whether a change has occurred
     */
    abstract fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean

    /**
     * Creates an instance of [SyncStage]
     * @param main The main entity id
     * @param other The other entities id
     * @param update Whether a block update is needed
     * @return A sync stage
     */
    abstract fun create(main: Int, other: Int, update: Boolean): T

    /**
     * Creates a movement sync stage based on how much an entity has moved since last update
     * @param position The entities current position
     * @param lastPosition The entities last position
     * @return Height, Region or Move SyncStage
     */
    internal fun createMovement(position: Position, lastPosition: Position): SyncStage? {
        Position.regionDelta(position, lastPosition) { deltaX, deltaY, deltaPlane ->
            return if (deltaX == 0 && deltaY == 0 && deltaPlane == 0) {
                null//No update needed
            } else if (deltaX == 0 && deltaY == 0 && deltaPlane != 0) {
                HeightPlayerSync.create(position, lastPosition)//Change player plane
            } else if (deltaX == -1 || deltaY == -1 || deltaX == 1 || deltaY == 1) {
                RegionPlayerSync.create(position, lastPosition)//Move to adjacent region
            } else {
                MoveGlobalPlayerSync.create(position, lastPosition)//Move to a non-adjacent region
            }
        }

        //Can't get here
        return null
    }
}