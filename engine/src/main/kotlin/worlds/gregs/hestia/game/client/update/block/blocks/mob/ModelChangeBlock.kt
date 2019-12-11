package worlds.gregs.hestia.game.client.update.block.blocks.mob

import worlds.gregs.hestia.api.client.update.block.UpdateBlock

data class ModelChangeBlock(override val flag: Int, val models: IntArray?, val colours: IntArray?, val textures: IntArray?) : UpdateBlock {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModelChangeBlock

        if (flag != other.flag) return false
        if (models != null) {
            if (other.models == null) return false
            if (!models.contentEquals(other.models)) return false
        } else if (other.models != null) return false
        if (colours != null) {
            if (other.colours == null) return false
            if (!colours.contentEquals(other.colours)) return false
        } else if (other.colours != null) return false
        if (textures != null) {
            if (other.textures == null) return false
            if (!textures.contentEquals(other.textures)) return false
        } else if (other.textures != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = flag
        result = 31 * result + (models?.contentHashCode() ?: 0)
        result = 31 * result + (colours?.contentHashCode() ?: 0)
        result = 31 * result + (textures?.contentHashCode() ?: 0)
        return result
    }

}