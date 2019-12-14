package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Body(var look: IntArray? = null) : Component() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Body

        if (look != null) {
            if (other.look == null) return false
            if (!look!!.contentEquals(other.look!!)) return false
        } else if (other.look != null) return false

        return true
    }

    override fun hashCode(): Int {
        return look?.contentHashCode() ?: 0
    }
}