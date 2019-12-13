package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Transform() : Component() {

    constructor(mobId: Int) : this() {
        this.mobId = mobId
    }

    var mobId: Int = -1
}