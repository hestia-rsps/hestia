package worlds.gregs.hestia.core.world.movement.model.components.types

import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.entity.entity.model.components.Position

@PooledWeaver
class MoveStep(x: Int = -1, y: Int = -1, plane: Int = -1) : Position(x, y, plane)