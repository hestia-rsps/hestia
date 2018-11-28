package worlds.gregs.hestia.game.plugins.entity.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class DisplayName : Component() {
    var name: String? = null
}