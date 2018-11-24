package world.gregs.hestia.game.component.map

import com.artemis.Component
import com.artemis.utils.IntBag
import world.gregs.hestia.game.systems.login.locationHash18Bit
import kotlin.collections.HashMap
import java.util.*


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