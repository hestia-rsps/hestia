package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy

@PooledWeaver
class Path() : Component() {
    constructor(strategy: RouteStrategy) : this() {
        this.strategy = strategy
    }
    lateinit var strategy: RouteStrategy
}