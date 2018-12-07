package worlds.gregs.hestia.game.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Skull() : Component() {

    constructor(skull: Int) : this() {
        this.skull = skull
    }

    var skull = -1
}