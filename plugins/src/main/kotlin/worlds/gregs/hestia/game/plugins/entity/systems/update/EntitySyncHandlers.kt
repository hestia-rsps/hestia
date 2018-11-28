package worlds.gregs.hestia.game.plugins.entity.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.region.systems.locationHash18Bit
import worlds.gregs.hestia.game.update.DisplayFlag

class EntitySyncHandlers : PassiveSystem() {
    private lateinit var playerSyncSystem: PlayerSyncSystem
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun initialize() {
        super.initialize()
        playerSyncSystem.addLocal(DisplayFlag.REMOVE) { player, local ->
            sendMovementUpdate(this, player, local)
        }

        playerSyncSystem.addGlobal(DisplayFlag.ADD) { player, global ->
            val position = positionMapper.get(global)
            if (position != null) {
                sendMovementUpdate(this, player, global)
                writeBits(6, position.xInRegion)
                writeBits(6, position.yInRegion)
                writeBits(1, 1)//Update
            }
        }
    }

    private fun sendMovementUpdate(packet: Packet.Builder, player: Int, other: Int) {
        val type = getUpdateType(player, other)
        if (type != null) {
            //Move to global players
            //Needs update
            packet.writeBits(1, 1)
            //Movement type
            packet.writeBits(2, type.movementType())
            //Handlers
            playerSyncSystem.invokeGlobal(type, packet, player, other)
        } else {
            //Remove
            packet.writeBits(1, 0)
        }
    }

    private fun getUpdateType(player: Int, other: Int): DisplayFlag? {
        if (!positionMapper.has(other)) {
            return null
        }
        val delta = getDelta(viewportMapper.get(player), positionMapper.get(other), other)
        return if (delta.x == 0 && delta.y == 0 && delta.plane == 0) {
            null//No update needed
        } else if (delta.x == 0 && delta.y == 0 && delta.plane != 0) {
            DisplayFlag.HEIGHT//Change player plane
        } else if (delta.x == -1 || delta.y == -1 || delta.x == 1 || delta.y == 1) {
            DisplayFlag.REGION//Move to adjacent region
        } else {
            DisplayFlag.MOVE//Move to a non-adjacent region
        }
    }

    private fun getDelta(viewport: Viewport, position: Position, other: Int): Position {
        val newHash = position.locationHash18Bit
        val oldHash = viewport.getHash(other)

        val new = Position.from(newHash)
        val old = Position.from(oldHash)

        return Position.delta(new, old)
    }
}