package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class AppearanceData(var hash: ByteArray? = null, var data: ByteArray? = null): Component() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppearanceData

        if (hash != null) {
            if (other.hash == null) return false
            if (!hash!!.contentEquals(other.hash!!)) return false
        } else if (other.hash != null) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data!!.contentEquals(other.data!!)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hash?.contentHashCode() ?: 0
        result = 31 * result + (data?.contentHashCode() ?: 0)
        return result
    }
}