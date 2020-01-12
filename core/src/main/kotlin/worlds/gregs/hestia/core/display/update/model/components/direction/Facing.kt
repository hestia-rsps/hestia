package worlds.gregs.hestia.core.display.update.model.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Facing(var x: Int = 0, var y: Int = -1) : Component()