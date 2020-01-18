package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class InterfaceDefinition(components: Array<Pair<Int, InterfaceComponentDefinition>>) : Map<Int, InterfaceComponentDefinition> by mapOf(*components), Definition {

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
    }
}