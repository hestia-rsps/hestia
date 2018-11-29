package worlds.gregs.hestia.game.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Emote() : Component() {

    constructor(id: Int) : this() {
        this.id = id
    }

    var id = -1
}