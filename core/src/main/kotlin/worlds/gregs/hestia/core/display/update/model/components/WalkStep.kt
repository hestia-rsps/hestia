package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.core.display.update.model.Direction

@PooledWeaver
data class WalkStep(var direction: Direction = Direction.NONE) : Component()