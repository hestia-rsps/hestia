package worlds.gregs.hestia.game.plugins.client.systems.network

import com.artemis.ComponentMapper
import io.netty.channel.Channel
import worlds.gregs.hestia.api.client.ClientNetwork
import worlds.gregs.hestia.api.client.components.Client
import worlds.gregs.hestia.api.client.components.NetworkSession

/**
 * ClientNetworkSystem
 * Sets up the client->server [NetworkSession]
 */
class ClientNetworkSystem : ClientNetwork() {

    private lateinit var networkSessionMapper: ComponentMapper<NetworkSession>
    private lateinit var clientMapper: ComponentMapper<Client>

    override fun setup(entityId: Int, channel: Channel) {
        networkSessionMapper.create(entityId).channel = channel
        clientMapper.create(entityId)
    }

}