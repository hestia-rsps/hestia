package worlds.gregs.hestia.core.display.client.logic.systems.network

import com.artemis.ComponentMapper
import io.netty.channel.Channel
import worlds.gregs.hestia.core.display.client.api.ClientNetwork
import worlds.gregs.hestia.core.display.client.model.components.Client
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession

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