package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class Animation() : Component() {

    constructor(id: Int, speed: Int = 0) : this() {
        this.id = id
        this.speed = speed
    }

    var id = -1
    var speed = 0
}