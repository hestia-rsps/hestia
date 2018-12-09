package worlds.gregs.hestia.game.plugins.core.components.map

import com.artemis.Component
import java.util.*
import kotlin.collections.HashMap

class Viewport : Component() {

    private val localMobs = ArrayList<Int>()

    private val localPlayers = ArrayList<Int>()

    private val regionPositions = HashMap<Int, Position>()

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