package worlds.gregs.hestia.game.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.path.RouteStrategy
import worlds.gregs.hestia.game.region.FixedTileStrategy

@PooledWeaver
class Path() : Component() {

    constructor(x: Int, y: Int, strategy: RouteStrategy, max: Int = -1, check: Boolean = true) : this() {
        this.x = x
        this.y = y
        this.strategy = strategy
        this.max = max
        this.check = check
    }

    var x = -1
    var y = -1
    var max = -1
    var check = true
    lateinit var strategy: RouteStrategy
}

fun Entity.path(x: Int, y: Int, max: Int = -1, check: Boolean = true) {
    edit().add(Path(x, y, FixedTileStrategy(x, y), max, check))
}