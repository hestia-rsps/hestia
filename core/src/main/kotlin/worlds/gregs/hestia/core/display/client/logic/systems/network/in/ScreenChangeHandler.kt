package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.components.GameFrame
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.ScreenChange

@Wire(failOnNull = false)
class ScreenChangeHandler : MessageHandlerSystem<ScreenChange>() {

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private lateinit var windows: Windows

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: ScreenChange) {
        if(gameFrameMapper.has(entityId)) {
            val (displayMode, width, height, antialias) = message
            val gameFrame = gameFrameMapper.get(entityId)
            gameFrame.width = width
            gameFrame.height = height

            if(gameFrame.displayMode == displayMode || !windows.hasWindow(entityId, Windows.GraphicsOptions)) {
                return
            }
            gameFrame.displayMode = displayMode
            windows.updateGameframe(entityId)
        }
    }

}