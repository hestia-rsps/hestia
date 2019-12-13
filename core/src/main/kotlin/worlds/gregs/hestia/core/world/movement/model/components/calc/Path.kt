package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy

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