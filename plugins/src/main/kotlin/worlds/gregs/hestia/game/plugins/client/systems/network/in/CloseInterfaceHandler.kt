package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.annotations.Wire
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.PacketHandler
import worlds.gregs.hestia.network.login.Packets

@PacketSize(0)
@PacketOpcode(Packets.CLOSE_INTERFACE)
@Wire(failOnNull = false)
class CloseInterfaceHandler : PacketHandler() {

    private var ui: UserInterface? = null

    override fun handle(entityId: Int, packet: Packet, length: Int) {
        ui?.close(entityId)
    }

}