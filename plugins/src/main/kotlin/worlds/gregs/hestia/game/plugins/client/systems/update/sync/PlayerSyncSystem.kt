package worlds.gregs.hestia.game.plugins.client.systems.update.sync

import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.GameConstants.PLAYERS_LIMIT
import worlds.gregs.hestia.api.update.EntitySync
import worlds.gregs.hestia.api.update.PlayerSync
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import java.util.*

class PlayerSyncSystem : BasePlayerSyncSystem(), EntitySync, PlayerSync {

    override val localHandlers = HashMap<DisplayFlag, PacketBuilder.(Int, Int) -> Unit>()
    override val globalHandlers = HashMap<DisplayFlag, PacketBuilder.(Int, Int) -> Unit>()
    private var skip = -1//Counter for the number of players to skip

    override fun begin() {
        skip = -1
    }

    override fun skip(locals: List<Int>, globals: List<Int>) {
        total = locals.size
        start()
        skip += locals.size
        middle()
        skip += globals.size
        total += globals.size
    }

    override fun local(entityId: Int, local: Int, type: DisplayFlag?, update: Boolean) {
        if (type == null) {
            skip++
        } else {
            skipPlayers()

            //Needs update
            packet.writeBits(1, 1)

            //Is mask update needed?
            packet.writeBits(1, if (type == DisplayFlag.RUNNING && entityId == local) 1 else if (type == DisplayFlag.REMOVE) 0 else update.int)

            //Movement type (0 none, 1 walk, 2 run, 3 tele)
            if (type != DisplayFlag.WALKING && type != DisplayFlag.RUNNING) {
                packet.writeBits(2, type.movementType())
            }

            //Handlers
            invokeLocal(packet, entityId, local, type)
        }
    }

    override fun middle() {
        skipPlayers()
        packet.finishBitAccess()
        packet.startBitAccess()
    }

    override fun global(entityId: Int, global: Int, type: DisplayFlag?, update: Boolean) {
        if (type == null) {
            skip++
        } else {
            skipPlayers()

            //Don't skip
            packet.writeBits(1, 1)

            //Movement type
            packet.writeBits(2, type.movementType())

            //Handlers
            invokeGlobal(type, packet, entityId, global)
        }
    }

    override fun process(entityId: Int) {
        super.process(entityId)

        //Skip remaining null players up to max player count
        skip += PLAYERS_LIMIT - 1 - total

        skipPlayers()

        packet.finishBitAccess()
    }

    private fun skipPlayers() {
        if (skip > -1) {
            packet.writeBits(1, 0)//No update needed
            when {
                skip == 0 -> {
                    packet.writeBits(2, 0)
                }
                skip < 32 -> {
                    packet.writeBits(2, 1)
                    packet.writeBits(5, skip)
                }
                skip < 256 -> {
                    packet.writeBits(2, 2)
                    packet.writeBits(8, skip)
                }
                skip < 2048 -> {
                    packet.writeBits(2, 3)
                    packet.writeBits(11, skip)
                }
            }
        }
        skip = -1
    }
}