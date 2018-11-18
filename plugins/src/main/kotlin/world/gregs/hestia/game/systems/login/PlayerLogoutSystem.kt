package world.gregs.hestia.game.systems.login

import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.network.out.Logout
import world.gregs.hestia.services.send

class PlayerLogoutSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class, ClientIndex::class, DisplayName::class)) {

    private lateinit var es: EventSystem

    override fun removed(entityId: Int) {
        es.send(entityId, Logout())
    }
}