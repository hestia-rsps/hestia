package worlds.gregs.hestia.core.display.client.model.components

import com.artemis.Component
import com.artemis.annotations.DelayedComponentRemoval
import com.artemis.annotations.PooledWeaver

@PooledWeaver
@DelayedComponentRemoval
class ClientIndex : Component() {
    var index = 0
    var remove = false
}