package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.annotations.Wire
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.network.game.Packets

@PacketInfo(0, Packets.CLOSE_INTERFACE)
@Wire(failOnNull = false)
class CloseInterfaceHandler : PacketHandlerSystem() {

    private var ui: UserInterface? = null

    override fun handle(entityId: Int, packet: Packet) {
        ui?.close(entityId)
    }

}