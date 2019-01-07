package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class Config(id: Int, value: Int, large: Boolean = value !in Byte.MIN_VALUE .. Byte.MAX_VALUE) : Packet.Builder(if(large) 39 else 101) {
    init {
        if(large) {
            writeInt2(value)
            writeShortA(id)
        } else {
            writeShort(id)
            writeByteA(value)
        }
    }
}