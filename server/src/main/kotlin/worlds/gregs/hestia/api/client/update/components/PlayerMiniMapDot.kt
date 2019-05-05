package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class PlayerMiniMapDot() : Component() {

    constructor(p: Boolean) : this() {
        this.p = p
    }

    var p = false
}