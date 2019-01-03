package worlds.gregs.hestia.game.plugins.player.component.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class PlayerMiniMapDot() : Component() {

    constructor(p: Boolean) : this() {
        this.p = p
    }

    var p = false
}