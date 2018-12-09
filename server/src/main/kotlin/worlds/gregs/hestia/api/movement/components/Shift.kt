package worlds.gregs.hestia.api.movement.components

import com.artemis.Component

class Shift() : Component() {
    var x: Int = 0
    var y: Int = 0
    var plane: Int = 0

    constructor(x: Int, y: Int, plane: Int = 0) : this() {
        this.x = x
        this.y = y
        this.plane = plane
    }

    fun add(offsetX: Int, offsetY: Int, offsetPlane: Int = 0) {
        this.x += offsetX
        this.y += offsetY
        this.plane += offsetPlane
    }
}