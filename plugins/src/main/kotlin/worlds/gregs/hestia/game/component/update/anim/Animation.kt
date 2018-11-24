package worlds.gregs.hestia.game.component.update.anim

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class Animation : Component() {
    var id = -1
    var speed = 0
}