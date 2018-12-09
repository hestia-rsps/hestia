package worlds.gregs.hestia.api.core.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ClientIndex : Component() {
    var index = 0
}