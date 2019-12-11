package worlds.gregs.hestia.core.entity.player.components.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Skull() : Component() {

    constructor(skull: Int) : this() {
        this.skull = skull
    }

    var skull = -1
}