package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * @param calculate Whether or not to calculate an extra step to prevent getting caught on corners
 */
@PooledWeaver
data class Beside(var x: Int = -1, var y: Int = -1, var max: Int = -1, var sizeX: Int = 1, var sizeY: Int = 1, var check: Boolean = true, var calculate: Boolean = false, var beside: Boolean = false) : Component()