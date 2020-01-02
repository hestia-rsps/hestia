package worlds.gregs.hestia.core.display.client.model.components

import com.artemis.Component
import worlds.gregs.hestia.GameConstants.PLAYERS_LIMIT
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.artemis.bag.map.LinkedSyncBag
import worlds.gregs.hestia.artemis.bag.map.OrderedSyncBag
import worlds.gregs.hestia.core.display.update.api.Synchronize.Companion.MAX_ENTITIES_PER_TICK
import worlds.gregs.hestia.core.entity.entity.model.components.Position

class Viewport : Component() {

    private val localMobs = LinkedSyncBag(MAX_ENTITIES_PER_TICK)

    private val localPlayers = OrderedSyncBag(PLAYERS_LIMIT, MAX_ENTITIES_PER_TICK)

    private val regionPositions = HashMap<Int, Position>()

    private val idlePlayerFlags = IntArray(PLAYERS_LIMIT)

    fun isSlotFree(index: Int): Boolean {
        return idlePlayerFlags[index] and 0x1 == 0
    }

    fun isSlotBlocked(index: Int) : Boolean {
        return idlePlayerFlags[index] and 0x1 != 0
    }

    fun setIdle(index: Int) {
        idlePlayerFlags[index] = idlePlayerFlags[index] or 2
    }

    fun shiftSlots() {
        for(i in idlePlayerFlags.indices) {
            idlePlayerFlags[i] = idlePlayerFlags[i] shr 1
        }
    }

    fun localMobs(): EntitySyncBag {
        return localMobs
    }

    fun localPlayers(): EntitySyncBag {
        return localPlayers
    }

    fun updatePosition(entityId: Int, tile: Position) {
        if(!regionPositions.containsKey(entityId)) {
            regionPositions[entityId] = Position.clone(tile)
        } else {
            regionPositions[entityId]!!.set(tile)
        }
    }

    fun getPosition(entityId: Int): Position {
        return regionPositions[entityId] ?: Position.EMPTY
    }
}