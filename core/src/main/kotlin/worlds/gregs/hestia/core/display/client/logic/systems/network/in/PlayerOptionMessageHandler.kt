package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.ActionContext
import worlds.gregs.hestia.core.action.logic.systems.PlayerOptionSystem
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.PlayerOptionMessage

class PlayerOptionMessageHandler : MessageHandlerSystem<PlayerOptionMessage>() {

    private val logger = LoggerFactory.getLogger(PlayerOptionMessageHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var options: PlayerOptionSystem
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: PlayerOptionMessage) {
        val (playerIndex, optionId) = message

        //Find player
        val viewport = viewportMapper.get(entityId)
        val playerId = viewport.localPlayers().getEntity(playerIndex)
        if(playerId == -1) {
            return logger.warn("Invalid player index $message")
        }

        //Find option
        val optionString = PlayerOptions.getOption(optionId)?.string ?: return logger.warn("Cannot find player option $optionId")
        val action = options.get(optionString, -1) ?: return logger.warn("Invalid player action $optionString")
        val context = actionContextMapper.get(entityId)
        action.invoke(context, playerId)
    }
}