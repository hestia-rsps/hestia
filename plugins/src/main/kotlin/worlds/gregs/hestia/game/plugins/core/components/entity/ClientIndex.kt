package worlds.gregs.hestia.game.plugins.core.components.entity

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ClientIndex : Component() {
    var index = 0
}