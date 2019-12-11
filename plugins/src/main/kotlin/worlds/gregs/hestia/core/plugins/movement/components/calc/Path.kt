package worlds.gregs.hestia.core.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.api.movement.RouteStrategy

@PooledWeaver
class Path() : Component() {

    constructor(strategy: RouteStrategy, collide: Boolean = true, alternative: Boolean = true) : this() {
        this.strategy = strategy
        this.collide = collide
        this.alternative = alternative
    }

    var collide = true
    var alternative = true
    lateinit var strategy: RouteStrategy
}