package worlds.gregs.hestia.game.systems.login

import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.game.component.NetworkSession
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.network.out.Logout
import worlds.gregs.hestia.services.send

class PlayerLogoutSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class, ClientIndex::class, DisplayName::class)) {

    private lateinit var es: EventSystem

    override fun removed(entityId: Int) {
        es.send(entityId, Logout())
    }
}