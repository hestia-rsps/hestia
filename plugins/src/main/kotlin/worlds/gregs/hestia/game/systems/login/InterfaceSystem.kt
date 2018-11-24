package worlds.gregs.hestia.game.systems.login

import worlds.gregs.hestia.game.component.NetworkSession
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.events.send
import worlds.gregs.hestia.game.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.network.out.WindowsPane

class InterfaceSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class)) {

    override fun inserted(entityId: Int) {
        val e = world.getEntity(entityId)
        e.send(WindowsPane(746, 0))
    }

}
