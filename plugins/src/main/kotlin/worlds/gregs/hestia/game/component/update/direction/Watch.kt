package worlds.gregs.hestia.game.component.update.direction

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Watch : Component() {
    var entity = 0//Entity id
}

fun Entity.watch(entityId: Int) {
    val watch = Watch()
    watch.entity = entityId
    edit().add(watch)
}