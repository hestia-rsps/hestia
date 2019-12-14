package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Route(var entityId: Int = -1, var alternative: Boolean = false, var success: (() -> Unit)? = null, var failure: (() -> Unit)? = null) : Component()