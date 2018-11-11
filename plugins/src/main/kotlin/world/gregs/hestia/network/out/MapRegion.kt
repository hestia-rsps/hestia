package world.gregs.hestia.network.out

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.systems.login.MapRegionSystem
import world.gregs.hestia.game.systems.login.locationHash30Bit
import world.gregs.hestia.GameConstants
import world.gregs.hestia.core.network.packets.Packet

class MapRegion(players: IntArray, viewport: Viewport, positionMapper: ComponentMapper<Position>, entityId: Int, position: Position, local: Boolean) : Packet.Builder(43, Packet.Type.VAR_SHORT) {
    init {
        if (local) {
            startBitAccess()
            //Send current player position
            writeBits(30, position.locationHash30Bit)
            //Add as first local player
            viewport.addLocalPlayer(entityId)

            //Update player locations
            players.filterNot { it == entityId }.forEach { player ->
                MapRegionSystem.updateHash(player, positionMapper.get(player))
                writeBits(18, MapRegionSystem.locationHashes.getOrDefault(player, 0))
                viewport.addGlobalPlayer(player)
            }

            //Iterate up to max number of players
            for(i in (players.size + 1)..GameConstants.PLAYERS_LIMIT) {
                writeBits(18, 0)
            }

            finishBitAccess()
        }
        writeByteC(0)//Map type
        writeByte(0)//Force next map load refresh
        writeLEShort(position.chunkX)
        writeShort(position.chunkY)
        for (regionId in 0 until 6) {//Local map region ids
            for (index in 0..3) {
                writeInt(0)
            }
        }
    }
}