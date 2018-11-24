package worlds.gregs.hestia.game.component.movement

import com.artemis.Component

class ShiftPosition : Component() {
    var x: Int = 0
    var y: Int = 0
    var plane: Int = 0

    fun add(offsetX: Int, offsetY: Int, offsetPlane: Int = 0) {
        this.x += offsetX
        this.y += offsetY
        this.plane += offsetPlane
    }
}