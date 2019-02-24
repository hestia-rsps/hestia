package worlds.gregs.hestia.game.update

import com.artemis.Component
import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.codec.packet.PacketBuilder

interface UpdateEncoder {

    val encode: PacketBuilder.(Int, Int) -> Unit

    fun <T : Component> get(componentMapper: ComponentMapper<T>, entityId: Int): T? {
        if (componentMapper.has(entityId)) {
            return componentMapper.get(entityId)
        }
        return null
    }
}