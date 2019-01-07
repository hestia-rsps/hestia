package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.network.game.Packets

@PacketInfo(4, Packets.WORLD_MAP_CLICK)
class WorldMapHandler : PacketHandlerSystem() {

    override fun handle(entityId: Int, packet: Packet) {
    }

}