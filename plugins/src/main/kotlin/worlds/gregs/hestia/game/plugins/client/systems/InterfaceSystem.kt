package worlds.gregs.hestia.game.plugins.client.systems

import worlds.gregs.hestia.game.api.SubscriptionSystem
import worlds.gregs.hestia.game.events.send
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.network.out.WindowsPane
import worlds.gregs.hestia.services.Aspect

class InterfaceSystem : SubscriptionSystem(Aspect.all(NetworkSession::class)) {

    override fun inserted(entityId: Int) {
        val e = world.getEntity(entityId)
        e.send(WindowsPane(746, 0))//548
    }

}
