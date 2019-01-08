package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.PacketHandler
import worlds.gregs.hestia.game.plugins.widget.systems.screen.GraphicsOptionsSystem
import worlds.gregs.hestia.network.login.Packets

@PacketSize(6)
@PacketOpcode(Packets.SCREEN)
@Wire(failOnNull = false)
class ScreenHandler : PacketHandler() {

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private var ui: UserInterface? = null

    override fun handle(entityId: Int, packet: Packet, length: Int) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            val displayMode = packet.readUnsignedByte()
            gameFrame.width = packet.readShort()
            gameFrame.height = packet.readShort()

            if(gameFrame.displayMode == displayMode || ui?.contains(entityId, GraphicsOptionsSystem::class) == true) {
                return
            }

            gameFrame.displayMode = displayMode
            ui?.reload(entityId)
        }
    }

}