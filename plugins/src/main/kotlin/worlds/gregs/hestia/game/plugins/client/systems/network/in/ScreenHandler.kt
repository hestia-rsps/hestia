package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketInfo
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.game.plugins.widget.systems.screen.GraphicsOptionsSystem
import worlds.gregs.hestia.network.game.Packets

@PacketInfo(6, Packets.SCREEN)
@Wire(failOnNull = false)
class ScreenHandler : PacketHandlerSystem() {

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private var ui: UserInterface? = null

    override fun handle(entityId: Int, packet: Packet) {
        if(gameFrameMapper.has(entityId)) {
            val gameFrame = gameFrameMapper.get(entityId)
            val displayMode = packet.readUnsignedByte()
            gameFrame.width = packet.readShort()
            gameFrame.height = packet.readShort()

            if(gameFrame.displayMode == displayMode || (ui != null && !ui!!.contains(entityId, GraphicsOptionsSystem::class))) {
                return
            }

            gameFrame.displayMode = displayMode
            ui?.reload(entityId)
        }
    }

}