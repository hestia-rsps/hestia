package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class WindowDefinition(size: Int) : MutableList<WidgetDefinition?> by mutableListOf(), Definition {

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
    }

}