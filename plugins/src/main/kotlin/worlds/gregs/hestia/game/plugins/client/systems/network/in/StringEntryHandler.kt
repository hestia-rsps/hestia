package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.core.components.ClientIndex
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.network.game.Packets
import worlds.gregs.hestia.network.social.out.FriendsChatName

@PacketInfo(-1, Packets.ENTER_STRING)
class StringEntryHandler : PacketHandlerSystem() {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    override fun handle(entityId: Int, packet: Packet) {
        //TODO handling/call back other things
        GameServer.worldSession?.write(FriendsChatName(entityId, packet.readString()))
    }

}