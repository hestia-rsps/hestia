package worlds.gregs.hestia.game.plugins.client.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketWriter

@PooledWeaver
class ClientMobPacket : Component() {
    var packet = PacketWriter(6, Packet.Type.VAR_SHORT)
}