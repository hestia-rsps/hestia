package world.gregs.hestia.game.systems.login

import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.events.send
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.network.out.WindowsPane

class InterfaceSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class)) {

    override fun inserted(entityId: Int) {
        val e = world.getEntity(entityId)
        e.send(WindowsPane(548, 0))
    }

}
