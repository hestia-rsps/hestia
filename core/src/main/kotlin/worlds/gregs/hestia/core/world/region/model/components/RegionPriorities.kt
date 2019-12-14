package worlds.gregs.hestia.core.world.region.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class RegionPriorities(var priority: Int = 0) : Component()