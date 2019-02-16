package worlds.gregs.hestia.network.game.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REGION
import worlds.gregs.hestia.network.game.encoders.messages.MapRegion

class MapRegionEncoder : MessageEncoder<MapRegion>() {

    override fun encode(builder: PacketBuilder, message: MapRegion) {
        val (entity, chunkX, chunkY, forceRefresh, mapSize, mapHash, login) = message
        builder.apply {
            writeOpcode(REGION, Packet.Type.VAR_SHORT)
            login?.invoke(this, entity)
            writeByte(mapSize, Modifier.INVERSE)//Map type
            writeByte(forceRefresh)//Force next map load refresh
            writeShort(chunkX, order = Endian.LITTLE)
            writeShort(chunkY)
            val regionCount = ((chunkX + mapHash) / 8 - (chunkX - mapHash) / 8 + 1) * ((chunkY + mapHash) / 8 - (chunkY - mapHash) / 8 + 1)
            //Needed as it is used to calculate number of regions
            for (regionId in 0 until regionCount) {
                for (index in 0 until 4) {
                    writeInt(0)//XTEA keys
                }
            }
        }
    }

}