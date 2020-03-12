package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import worlds.gregs.hestia.network.client.encoders.messages.Cutscene
import worlds.gregs.hestia.service.Xteas

class CutsceneEncoder : MessageEncoder<Cutscene>() {

    override fun encode(builder: PacketBuilder, message: Cutscene) {
        val (id, chunkX, chunkY, data) = message
        builder.apply {
            writeOpcode(132, Packet.Type.VAR_SHORT)
            writeShort(id)
            writeShort(6)
            forNearbyRegions(chunkX, chunkY, 6) { regionId ->
                val keys = Xteas.KEY_TABLE[regionId]
                for (index in 0 until 4) {
                    writeInt(keys?.get(index) ?: 0)
                }
            }
            writeByte(data.size)
            writeBytes(data)
        }
    }

    companion object {

        private fun forNearbyRegions(chunkX: Int, chunkY: Int, mapHash: Int, action: (Int) -> Unit) {
            for (regionX in (chunkX - mapHash) / 8..(chunkX + mapHash) / 8) {
                for (regionY in (chunkY - mapHash) / 8..(chunkY + mapHash) / 8) {
                    action(regionY + (regionX shl 8))
                }
            }
        }
    }

}