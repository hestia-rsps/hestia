package worlds.gregs.hestia.game.plugins.core.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class Animation : Component() {
    var id = -1
    var speed = 0
}