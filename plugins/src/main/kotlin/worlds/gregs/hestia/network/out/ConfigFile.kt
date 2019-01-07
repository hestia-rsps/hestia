package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class ConfigFile(fileId: Int, value: Int, large: Boolean = value !in Byte.MIN_VALUE .. Byte.MAX_VALUE) : Packet.Builder(if(large) 84 else 14) {
    init {
        if(large) {
            writeInt(value)
            writeShort(fileId)
        } else {
            writeByteS(value)
            writeShortA(fileId)
        }
    }
}