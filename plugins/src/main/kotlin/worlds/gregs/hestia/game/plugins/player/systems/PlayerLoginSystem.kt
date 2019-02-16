package worlds.gregs.hestia.game.plugins.player.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.messages.PlayerLogin
import world.gregs.hestia.core.network.protocol.messages.PlayerReconnect
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.api.core.components.ClientIndex
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.game.events.SocialLogin
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import worlds.gregs.hestia.services.Aspect

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