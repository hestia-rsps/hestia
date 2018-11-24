package world.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class UpdateClanMember : Component() {
    var inSameClanChat = false
}

fun Entity.updateClanChat(sameClan: Boolean) {
    val clan = UpdateClanMember()
    clan.inSameClanChat = sameClan
    edit().add(clan)
}