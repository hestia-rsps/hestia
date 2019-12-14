package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Colours(var colours: IntArray? = null) : Component() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Colours

        if (colours != null) {
            if (other.colours == null) return false
            if (!colours!!.contentEquals(other.colours!!)) return false
        } else if (other.colours != null) return false

        return true
    }

    override fun hashCode(): Int {
        return colours?.contentHashCode() ?: 0
    }
}