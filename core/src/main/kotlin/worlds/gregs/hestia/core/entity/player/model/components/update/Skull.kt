package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Skull() : Component() {

    constructor(skull: Int) : this() {
        this.skull = skull
    }

    var skull = -1
}