package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import worlds.gregs.hestia.game.PacketHandler
import worlds.gregs.hestia.network.login.Packets

@PacketSize(4)
@PacketOpcode(Packets.WORLD_MAP_CLICK)
class WorldMapHandler : PacketHandler() {

    override fun handle(entityId: Int, packet: Packet, length: Int) {
    }

}