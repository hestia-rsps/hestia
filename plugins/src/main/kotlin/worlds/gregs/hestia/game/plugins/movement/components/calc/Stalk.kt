package worlds.gregs.hestia.game.plugins.movement.components.calc

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Stalk() : Component() {

    constructor(entityId: Int) : this() {
        this.entity = entityId
    }

    var entity = 0
}