package worlds.gregs.hestia.game.client

import com.artemis.Component
import worlds.gregs.hestia.game.entity.Position
import java.util.*
import kotlin.collections.HashMap

class Viewport : Component() {

    private val localMobs = ArrayList<Int>()

    private val localPlayers = ArrayList<Int>()

    private val regionPositions = HashMap<Int, Position>()

    private val idlePlayerFlags = IntArray(2048)

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

    fun addLocalMob(mob: Int) {
        localMobs.add(mob)
    }

    fun localMobs(): ArrayList<Int> {
        return localMobs
    }

    fun addLocalPlayer(player: Int) {
        localPlayers.add(player)
        localPlayers.sort()
    }

    fun localPlayers(): ArrayList<Int> {
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