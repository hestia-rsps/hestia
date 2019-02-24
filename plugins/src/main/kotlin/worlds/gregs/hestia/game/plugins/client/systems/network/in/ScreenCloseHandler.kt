package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer.Companion.gameMessages
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.network.game.decoders.messages.ScreenClose

@Wire(failOnNull = false)
class ScreenCloseHandler : MessageHandlerSystem<ScreenClose>() {
    private var ui: UserInterface? = null

    override fun initialize() {
        super.initialize()
        gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: ScreenClose) {
        ui?.close(entityId)
    }

}
