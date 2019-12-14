package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy

@PooledWeaver

data class Path(var collide: Boolean = true, var alternative: Boolean = true) : Component() {
    lateinit var strategy: RouteStrategy
}