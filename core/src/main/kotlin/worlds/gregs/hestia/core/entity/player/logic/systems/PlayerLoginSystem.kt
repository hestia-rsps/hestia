package worlds.gregs.hestia.core.entity.player.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.messages.PlayerLogin
import world.gregs.hestia.core.network.protocol.messages.PlayerReconnect
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.artemis.events.SocialLogin
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.artemis.Aspect

class PlayerLoginSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Player::class, DisplayName::class)) {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    override fun inserted(entityId: Int) {
        login(entityId, false)
    }

    @Subscribe
    fun event(event: SocialLogin) {
        login(event.entityId, true)
    }

    private fun login(entityId: Int, reconnect: Boolean) {
        val displayName = displayNameMapper.get(entityId)
        val clientIndex = clientIndexMapper.get(entityId)
        val name = displayName.name ?: ""

        //Notify social server
        GameServer.worldSession?.write(if(reconnect) PlayerReconnect(name, entityId, clientIndex.index) else PlayerLogin(name, entityId, clientIndex.index), true)
    }
}