package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy

@PooledWeaver

data class Path(var collide: Boolean = true, var alternative: Boolean = true) : Component() {
    constructor(strategy: RouteStrategy, collide: Boolean, alternative: Boolean) : this(collide, alternative) {
        this.strategy = strategy
    }
    lateinit var strategy: RouteStrategy
}