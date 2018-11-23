package world.gregs.hestia.network.out

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.systems.login.locationHash30Bit
import world.gregs.hestia.game.systems.sync.player.PlayerIndexSystem
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.game.systems.login.locationHash18Bit

class MapRegion(players: IntArray, viewport: Viewport, positionMapper: ComponentMapper<Position>, entityId: Int, position: Position, local: Boolean) : Packet.Builder(43, Packet.Type.VAR_SHORT) {
    init {
        println("Local $local")
        if (local) {
            startBitAccess()
            //Send current player position
            writeBits(30, position.locationHash30Bit)

            //Update player locations
            players.filterNot { it == entityId }.forEach { player ->
                val hash = positionMapper.get(player).locationHash18Bit
                viewport.updateHash(player, positionMapper.get(player))
                writeBits(18, hash)
            }

            //Iterate up to max number of players
            for(i in (players.size + 1) until PlayerIndexSystem.PLAYERS_LIMIT) {
                writeBits(18, 0)
            }

            finishBitAccess()
        }
        writeByteC(0)//Map type
        writeByte(0)//Force next map load refresh
        writeLEShort(position.chunkX)
        writeShort(position.chunkY)
        for (regionId in 0 until 6) {//Local map region ids
            for (index in 0 until 4) {
                writeInt(0)
            }
        }
    }
}