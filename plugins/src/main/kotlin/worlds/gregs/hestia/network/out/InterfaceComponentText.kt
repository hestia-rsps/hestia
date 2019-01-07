package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class InterfaceComponentText(interfaceId: Int, componentId: Int, text: String) : Packet.Builder(33, Packet.Type.VAR_SHORT) {

    init {
        writeInt(interfaceId shl 16 or componentId)
        writeString(text)
    }

}