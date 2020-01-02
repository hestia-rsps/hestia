package worlds.gregs.hestia.network.client.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REGION
import worlds.gregs.hestia.GameConstants
import worlds.gregs.hestia.network.client.encoders.messages.MapRegion
import worlds.gregs.hestia.service.Xteas

class MapRegionEncoder : MessageEncoder<MapRegion>() {

    override fun encode(builder: PacketBuilder, message: MapRegion) {
        val (chunkX, chunkY, forceRefresh, mapSize, mapHash, positions, location) = message
        builder.apply {
            writeOpcode(REGION, Packet.Type.VAR_SHORT)
            if(positions != null && location != null) {
                startBitAccess()
                //Send current player position
                writeBits(30, location)

                //Update player locations
                positions.forEach { hash ->
                    writeBits(18, hash)
                }

                //Iterate up to max number of players
                //Positions doesn't include self & not zero indexed so +2
                for(i in positions.size + 2 until GameConstants.PLAYERS_LIMIT) {
                    writeBits(18, 0)
                }

                finishBitAccess()
            }
            writeByte(mapSize, Modifier.INVERSE)//Map type
            writeByte(forceRefresh)//Force next map load refresh
            writeShort(chunkX, order = Endian.LITTLE)
            writeShort(chunkY)
            //Needed as it is used to calculate number of regions
            forNearbyRegions(chunkX, chunkY, mapHash) { regionId ->
                val keys = Xteas.KEY_TABLE[regionId]
                for (index in 0 until 4) {
                    writeInt(keys?.get(index) ?: 0)
                }
            }
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