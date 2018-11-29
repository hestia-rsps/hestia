package worlds.gregs.hestia.game.plugins.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Rotation() : Component() {

    constructor(rotation: Int) : this() {
        this.rotation = rotation
    }

    var rotation = -1
    private var regionCoords: Array<Array<Array<IntArray>>>? = null
}