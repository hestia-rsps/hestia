package worlds.gregs.hestia.core.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Route() : Component() {

    constructor(entityId: Int, alternative: Boolean = false, success: (() -> Unit)? = null, failure: (() -> Unit)? = null) : this() {
        this.entityId = entityId
        this.success = success
        this.failure = failure
        this.alternative = alternative
    }

    var entityId = -1
    var success: (() -> Unit)? = null
    var failure: (() -> Unit)? = null
    var alternative = false
}