package worlds.gregs.hestia.api.client.components

import com.artemis.Component
import com.artemis.annotations.EntityId
import com.artemis.annotations.PooledWeaver
import io.netty.channel.Channel

@PooledWeaver
class NetworkSession : Component() {
    @EntityId
    @JvmField
    var entity: Int = -1
    lateinit var channel: Channel
}