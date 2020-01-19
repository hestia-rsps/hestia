package worlds.gregs.hestia.core.world.movement.model.components.types

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.world.movement.model.MovementType

@PooledWeaver
data class Movement(var actual: MovementType = MovementType.None, var visual: MovementType = MovementType.None) : Component() {

    fun set(actual: MovementType, visual: MovementType = actual) {
        this.visual = visual
        this.actual = actual
    }
}