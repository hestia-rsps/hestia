package worlds.gregs.hestia.core.world.movement.model.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Stalk(var entity: Int = 0) : Component()