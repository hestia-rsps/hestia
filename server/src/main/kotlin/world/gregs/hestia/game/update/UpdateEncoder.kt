package world.gregs.hestia.game.update

import com.artemis.Component
import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet

interface UpdateEncoder {

    val encode: Packet.Builder.(Int, Int) -> Unit

    fun <T : Component> get(componentMapper: ComponentMapper<T>, entityId: Int): T? {
        if (componentMapper.has(entityId)) {
            return componentMapper.get(entityId)
        }
        return null
    }
}