package worlds.gregs.hestia.game.plugins.entity.components.update.direction

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Watch() : Component() {

    constructor(entityId: Int) : this() {
        this.entity = entityId
    }

    var entity = 0//Entity id
}