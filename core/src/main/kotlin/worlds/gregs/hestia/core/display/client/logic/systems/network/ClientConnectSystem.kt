package worlds.gregs.hestia.core.display.client.logic.systems.network

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.player.model.components.Member
import worlds.gregs.hestia.network.client.encoders.messages.LoginDetails
import worlds.gregs.hestia.network.world.handlers.PlayerLoginSuccessHandler

class ClientConnectSystem : SubscriptionSystem(Aspect.all(NetworkSession::class, ClientIndex::class, DisplayName::class)) {

    private lateinit var es: EventSystem
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var memberMapper: ComponentMapper<Member>

    override fun inserted(entityId: Int) {
        val displayName = displayNameMapper.get(entityId)
        val clientIndex = clientIndexMapper.get(entityId)
        val isMember = memberMapper.has(entityId)
        val name = displayName?.name ?: ""
        es.send(entityId, LoginDetails(clientIndex.index, name, if (name.equals("Greg", true)) 2 else 0, isMember = isMember, membersWorld = true))
        PlayerLoginSuccessHandler.switchPipeline(name)
    }
}