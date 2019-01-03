package worlds.gregs.hestia.game.plugins.player.component.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class UpdateClanMember() : Component() {

    constructor(inSameClanChat: Boolean) : this() {
        this.inSameClanChat = inSameClanChat
    }

    var inSameClanChat = false
}