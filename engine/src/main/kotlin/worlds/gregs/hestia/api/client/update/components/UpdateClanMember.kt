package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class UpdateClanMember() : Component() {//TODO rename? isClanMember?

    constructor(inSameClanChat: Boolean) : this() {
        this.inSameClanChat = inSameClanChat
    }

    var inSameClanChat = false
}