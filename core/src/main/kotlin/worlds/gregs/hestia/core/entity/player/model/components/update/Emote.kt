package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Emote() : Component() {

    constructor(id: Int) : this() {
        this.id = id
    }

    var id = -1
}