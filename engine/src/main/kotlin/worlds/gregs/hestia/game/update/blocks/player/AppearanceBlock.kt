package worlds.gregs.hestia.game.update.blocks.player

import worlds.gregs.hestia.game.update.UpdateBlock

data class AppearanceBlock(override val flag: Int, val data: ByteArray) : UpdateBlock {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppearanceBlock

        if (flag != other.flag) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = flag
        result = 31 * result + data.contentHashCode()
        return result
    }

}