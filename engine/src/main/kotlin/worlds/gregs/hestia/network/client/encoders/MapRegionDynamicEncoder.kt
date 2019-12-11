package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.DYNAMIC_REGION
import worlds.gregs.hestia.network.client.encoders.messages.MapRegionDynamic

class MapRegionDynamicEncoder : MessageEncoder<MapRegionDynamic>() {

    override fun encode(builder: PacketBuilder, message: MapRegionDynamic) {
        val (entity, chunkX, chunkY, forceReload, mapSize, mapHash, login, chunks, chunkCount) = message
        builder.apply {
            writeOpcode(DYNAMIC_REGION, Packet.Type.VAR_SHORT)
            login?.invoke(this, entity)
            writeShort(chunkY)
            writeByte(mapSize)
            writeByte(forceReload, Modifier.SUBTRACT)
            writeByte(3, Modifier.SUBTRACT)//Was at dynamic region? 5 or 3 TODO test
            writeShort(chunkX, order = Endian.LITTLE)

            startBitAccess()

            chunks.forEach { data ->
                writeBits(1, data != null)
                if(data != null) {
                    writeBits(26, data)
                }
            }

            finishBitAccess()

            //XTEA keys (values no longer needed but count is used to calculate chunk count)
            for (index in 0 until chunkCount) {
                for (keyIndex in 0 until 4) {
                    writeInt(0)
                }
            }
        }
    }

}