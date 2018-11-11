package world.gregs.hestia.game.component.map

import com.artemis.Component
import com.artemis.utils.IntBag
import java.util.*

class Viewport : Component() {

    private val localMobs = ArrayList<Int>()

    private val localPlayers = ArrayList<Int>()

    private val globalPlayerIndices = ArrayList<Int>()

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

    fun addGlobalPlayer(playerIndex: Int) {
        globalPlayerIndices.add(playerIndex)//TODO is a sort needed for these too?
    }

    fun globalPlayers(): ArrayList<Int> {
        return globalPlayerIndices
    }

    fun update(players: IntBag) {
        globalPlayerIndices.clear()
        globalPlayerIndices.addAll(Arrays.copyOf(players.data, players.size()).toList())
        globalPlayerIndices.removeAll(localPlayers)
    }
}