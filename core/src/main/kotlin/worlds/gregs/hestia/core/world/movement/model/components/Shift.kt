package worlds.gregs.hestia.core.world.movement.model.components

import com.artemis.Component

data class Shift(var x: Int = 0, var y: Int = 0, var plane: Int = 0) : Component() {

    fun add(offsetX: Int, offsetY: Int, offsetPlane: Int = 0) {
        this.x += offsetX
        this.y += offsetY
        this.plane += offsetPlane
    }

}