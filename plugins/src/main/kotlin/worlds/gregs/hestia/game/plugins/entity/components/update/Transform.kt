package worlds.gregs.hestia.game.plugins.entity.components.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.events.updateAppearance

@PooledWeaver
class Transform() : Component() {

    constructor(mobId: Int) : this() {
        this.mobId = mobId
    }

    var mobId: Int = -1
}

fun Entity.transform(id: Int) {
    edit().add(Transform(id))
    updateAppearance()//Player only
}