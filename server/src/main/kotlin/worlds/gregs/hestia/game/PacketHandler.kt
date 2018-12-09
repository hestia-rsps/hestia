package worlds.gregs.hestia.game

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.load.PacketMap

/**
 * PacketHandler
 * Template for systems handling inbound [worlds.gregs.hestia.game.events.ClientPacket]s
 */
abstract class PacketHandler : PassiveSystem() {

    /**
     * Handles incoming packet
     * @param entityId The entity of the client whom sent the packet
     * @param packet The packet received
     * @param length The packet length read
     */
    abstract fun handle(entityId: Int, packet: Packet, length: Int)

    companion object {
        //FIXME Temporary solution
        val gamePackets = PacketMap<PacketHandler>()
    }
}