package worlds.gregs.hestia.game.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Body : Component() {
    var look: IntArray? = null
}