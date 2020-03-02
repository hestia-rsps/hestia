package worlds.gregs.hestia.core.display.client.logic.systems.network

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.protocol.messages.PlayerLogout
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.components.ExitLobby
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.client.model.events.DisconnectClient
import worlds.gregs.hestia.network.client.encoders.messages.Logout

class ClientDisconnectSystem : SubscriptionSystem(Aspect.all(NetworkSession::class)) {

    private lateinit var es: EventSystem
    private lateinit var exitLobbyMapper: ComponentMapper<ExitLobby>

    override fun removed(entityId: Int) {
        if(world.entityManager.isActive(entityId)) {
            val toLobby = exitLobbyMapper.has(entityId)
            es.send(entityId, Logout(toLobby))
            es.perform(entityId, DisconnectClient())

            //Notify social server
            if(!toLobby) {
                GameServer.worldSession?.write(PlayerLogout(entityId), true)
            }
        }
    }
}