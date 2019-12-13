package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.annotations.Wire
import worlds.gregs.hestia.GameServer.Companion.gameMessages
import worlds.gregs.hestia.core.display.widget.api.UserInterface
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.ScreenClose

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
