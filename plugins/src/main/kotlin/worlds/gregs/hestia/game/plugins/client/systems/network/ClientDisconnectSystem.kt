package worlds.gregs.hestia.game.plugins.client.systems.network

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.client.components.ExitLobby
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.network.game.out.Logout
import worlds.gregs.hestia.network.social.out.PlayerLogout
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.send

class ClientDisconnectSystem : SubscriptionSystem(Aspect.all(NetworkSession::class)) {

    private lateinit var es: EventSystem
    private lateinit var exitLobbyMapper: ComponentMapper<ExitLobby>

    override fun removed(entityId: Int) {
        if(world.entityManager.isActive(entityId)) {
            val toLobby = exitLobbyMapper.has(entityId)
            es.send(entityId, Logout(toLobby))

            //Notify social server
            if(!toLobby) {
                GameServer.worldSession?.write(PlayerLogout(entityId))
            }
        }
    }
}