package worlds.gregs.hestia.api.client.update.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Face() : Component() {

    constructor(x: Int, y: Int) : this() {
        this.x = x
        this.y = y
    }

    var x = 0
    var y = -1
}