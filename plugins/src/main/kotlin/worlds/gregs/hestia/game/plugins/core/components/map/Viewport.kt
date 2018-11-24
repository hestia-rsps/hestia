package worlds.gregs.hestia.game.plugins.core.components.map

import com.artemis.Component
import worlds.gregs.hestia.game.plugins.region.systems.locationHash18Bit
import java.util.*
import kotlin.collections.HashMap

class Viewport : Component() {

    private val localMobs = ArrayList<Int>()

    private val localPlayers = ArrayList<Int>()

    private val locationHashes = HashMap<Int, Int>()

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

    fun updateHash(index: Int, tile: Position) {
        locationHashes[index] = tile.locationHash18Bit
    }

    fun getHash(playerIndex: Int): Int {
        return locationHashes[playerIndex] ?: Position.EMPTY.locationHash18Bit
    }
}