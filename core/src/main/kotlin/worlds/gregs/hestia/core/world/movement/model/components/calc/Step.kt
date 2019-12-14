package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Step(var x: Int = -1, var y: Int = -1, var max: Int = -1, var check: Boolean = false) : Component()