package worlds.gregs.hestia.core.world.movement.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Follow() : Component() {

    constructor(entityId: Int) : this() {
        this.entity = entityId
    }

    var entity = 0
}