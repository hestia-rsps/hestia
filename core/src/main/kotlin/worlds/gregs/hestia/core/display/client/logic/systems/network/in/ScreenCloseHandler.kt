package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer.Companion.gameMessages
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.ScreenClose

@Wire(failOnNull = false)
class ScreenCloseHandler : MessageHandlerSystem<ScreenClose>() {
    private var windows: Windows? = null

    override fun initialize() {
        super.initialize()
        gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: ScreenClose) {
        windows?.closeWindows(entityId, WindowPane.MAIN_SCREEN)
    }

}
