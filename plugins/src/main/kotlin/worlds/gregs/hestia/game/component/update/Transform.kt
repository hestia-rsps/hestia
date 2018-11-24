package worlds.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.events.updateAppearance

@PooledWeaver
class Transform : Component() {
    var mobId: Int = -1
}

fun Entity.transform(id: Int) {
    val transform = Transform()
    transform.mobId = id
    edit().add(transform)
    updateAppearance()//Player only
}