package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.map.MapConstants.MAP_SIZES

class MapRegion(entityId: Int, position: Position, forceRefresh: Boolean, login: (Packet.Builder.(Int) -> Unit)?) : Packet.Builder(43, Packet.Type.VAR_SHORT) {
    init {
        login?.invoke(this, entityId)
        writeByteC(0)//Map type
        writeByte(forceRefresh.int)//Force next map load refresh
        writeLEShort(position.chunkX)
        writeShort(position.chunkY)
        val chunkX = position.chunkX
        val chunkY= position.chunkY
        val mapHash = MAP_SIZES[0] shr 4
        val regionCount = ((chunkX + mapHash) / 8 - (chunkX - mapHash) / 8 + 1) * ((chunkY + mapHash) / 8 - (chunkY - mapHash) / 8 + 1)
        //Needed as it is used to calculate number of regions
        for (regionId in 0 until regionCount) {
            for (index in 0 until 4) {
                writeInt(0)//XTEA keys
            }
        }
    }
}