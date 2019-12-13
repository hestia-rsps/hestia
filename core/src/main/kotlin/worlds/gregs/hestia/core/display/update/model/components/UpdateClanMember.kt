package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class UpdateClanMember() : Component() {//TODO rename? isClanMember?

    constructor(inSameClanChat: Boolean) : this() {
        this.inSameClanChat = inSameClanChat
    }

    var inSameClanChat = false
}