package worlds.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.game.component.NetworkSession
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.network.login.out.LoginDetails
import worlds.gregs.hestia.services.send

class PlayerLoginSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, Player::class, ClientIndex::class, DisplayName::class)) {

    private lateinit var es: EventSystem
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    override fun inserted(entityId: Int) {
        val displayName = displayNameMapper.get(entityId)
        val clientIndex = clientIndexMapper.get(entityId)
        val name = displayName?.name ?: ""
        es.send(entityId, LoginDetails(clientIndex.index, name, if(name.equals("Greg", true)) 2 else 0))
    }
}