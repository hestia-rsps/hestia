package worlds.gregs.hestia.core.world.movement.model.components.types

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class MoveStep(var x: Int = -1, var y: Int = -1, var plane: Int = -1) : Component()