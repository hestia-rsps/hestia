package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.game.plugins.widget.systems.screen.GraphicsOptionsSystem
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