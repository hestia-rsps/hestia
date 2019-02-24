package worlds.gregs.hestia.game.plugins.client.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketWriter

@PooledWeaver
class ClientPlayerPacket : Component() {
    var packet = PacketWriter(69, Packet.Type.VAR_SHORT)
}