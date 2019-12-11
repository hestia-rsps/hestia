package worlds.gregs.hestia.api.client.update.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Watch() : Component() {

    constructor(entityId: Int) : this() {
        this.entity = entityId
    }

    var entity = 0//Entity id
}