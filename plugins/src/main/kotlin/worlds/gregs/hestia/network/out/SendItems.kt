package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int

class SendItems(key: Int, items: List<Int>, negativeKey: Boolean = key < 0) : Packet.Builder(37, Packet.Type.VAR_SHORT) {
    init {
        writeShort(if (negativeKey) key else key)
        writeByte(negativeKey.int)
        writeShort(items.size)
        for (item in items) {
            val amount = 1
            writeByte(if (amount >= 255) 255 else amount)
            if (amount >= 255) {
                writeInt(amount)
            }
            writeLEShort(item + 1)
        }
    }
}