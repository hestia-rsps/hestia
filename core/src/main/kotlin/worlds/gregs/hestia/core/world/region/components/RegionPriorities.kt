package worlds.gregs.hestia.core.world.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class RegionPriorities : Component() {
    var priority = 0
}