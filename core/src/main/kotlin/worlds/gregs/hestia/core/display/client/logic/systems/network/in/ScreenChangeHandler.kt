package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.widget.api.GameFrame
import worlds.gregs.hestia.core.display.widget.api.UserInterface
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.core.display.widget.logic.systems.screen.GraphicsOptionsSystem
import worlds.gregs.hestia.network.client.decoders.messages.ScreenChange

@Wire(failOnNull = false)
class ScreenChangeHandler : MessageHandlerSystem<ScreenChange>() {

    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private var ui: UserInterface? = null

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

            if(gameFrame.displayMode == displayMode || (ui != null && !ui!!.contains(entityId, GraphicsOptionsSystem::class))) {
                return
            }

            gameFrame.displayMode = displayMode
            ui?.reload(entityId)
        }
    }

}