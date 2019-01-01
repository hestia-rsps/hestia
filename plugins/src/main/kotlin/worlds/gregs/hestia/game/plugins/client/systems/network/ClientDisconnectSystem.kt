package worlds.gregs.hestia.game.plugins.client.systems.network

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.network.out.Logout
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.send

class ClientDisconnectSystem : SubscriptionSystem(Aspect.all(NetworkSession::class)) {

    private lateinit var es: EventSystem

    override fun removed(entityId: Int) {
        es.send(entityId, Logout())
    }
}