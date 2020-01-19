package worlds.gregs.hestia.core.display.update.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.HeightPlayerSync
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.MoveGlobalPlayerSync
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.RegionPlayerSync
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.game.update.SyncStage

/**
 * A passive system used to create [SyncStage]s
 * @param local Whether factory creates local stages
 * @param npc Whether factory creates npc stages
 */
abstract class SyncFactory<T : SyncStage>(open val local: Boolean, open val npc: Boolean, open val active: Boolean) : PassiveSystem() {

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