package worlds.gregs.hestia.game.update.blocks

import worlds.gregs.hestia.game.update.UpdateBlock

data class HitsBlock(override val flag: Int, val hits: List<Marker>, val player: Int, val other: Int) : UpdateBlock

class Marker(val source: Int, val damage: Int, val mark: Int, val delay: Int = 0, val critical: Boolean = false, val soak: Int = -1) {

    companion object {
        const val MISSED = 8
        const val REGULAR = 3
        const val MELEE = 0
        const val RANGE = 1
        const val MAGIC = 2
        const val REFLECTED = 4
        const val ABSORB = 5
        const val POISON = 6
        const val DISEASED = 7
        const val HEALED = 9
        const val CANNON = 13
    }
}