package worlds.gregs.hestia.game.plugins.client.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import world.gregs.hestia.core.network.packets.Packet

@PooledWeaver
class ClientMobPacket : Component() {
    var packet = Packet.Builder(6, Packet.Type.VAR_SHORT)
}