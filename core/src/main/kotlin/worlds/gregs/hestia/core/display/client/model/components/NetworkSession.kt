package worlds.gregs.hestia.core.display.client.model.components

import com.artemis.Component
import com.artemis.annotations.EntityId
import com.artemis.annotations.PooledWeaver
import io.netty.channel.Channel

@PooledWeaver
data class NetworkSession(@EntityId @JvmField var entity: Int = -1) : Component() {
    lateinit var channel: Channel
}