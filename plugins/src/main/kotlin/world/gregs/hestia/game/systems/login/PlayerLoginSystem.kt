package world.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.network.login.out.LoginDetails
import world.gregs.hestia.services.send

class PlayerLoginSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class, ClientIndex::class, DisplayName::class)) {

    private lateinit var es: EventSystem
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    override fun inserted(entityId: Int) {
        val displayName = displayNameMapper.get(entityId)
        val clientIndex = clientIndexMapper.get(entityId)
        es.send(entityId, LoginDetails(clientIndex.index, displayName?.name
                ?: "", 2))
    }
}