package worlds.gregs.hestia.core.world.movement.model.components

import com.artemis.Component
import com.artemis.annotations.EntityId
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class RunToggled : Component() {
    @EntityId
    @JvmField
    var entity = -1
}