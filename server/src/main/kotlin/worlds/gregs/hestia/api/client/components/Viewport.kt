package worlds.gregs.hestia.api.client.components

import com.artemis.Component
import worlds.gregs.hestia.GameConstants.PLAYERS_LIMIT
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.artemis.bag.LinkedSyncBag
import worlds.gregs.hestia.artemis.bag.OrderedSyncBag
import worlds.gregs.hestia.game.entity.components.Position

class Viewport : Component() {

    private val localMobs = LinkedSyncBag()

    private val localPlayers = OrderedSyncBag(PLAYERS_LIMIT)

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
        for(i in 0 until idlePlayerFlags.size) {
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