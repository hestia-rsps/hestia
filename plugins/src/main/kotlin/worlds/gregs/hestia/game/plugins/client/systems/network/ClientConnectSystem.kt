package worlds.gregs.hestia.game.plugins.client.systems.network

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.api.core.components.ClientIndex
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import worlds.gregs.hestia.network.login.out.LoginDetails
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.send

class ClientConnectSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, ClientIndex::class, DisplayName::class)) {

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