package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class GlobalConfig(id: Int, value: Int, large: Boolean = value !in Byte.MIN_VALUE .. Byte.MAX_VALUE) : Packet.Builder(if(large) 112 else 111) {
    init {
        if(large) {
            writeLEShort(id)
            writeInt(value)
        } else {
            writeLEShortA(id)
            writeByteS(value)
        }
    }
}