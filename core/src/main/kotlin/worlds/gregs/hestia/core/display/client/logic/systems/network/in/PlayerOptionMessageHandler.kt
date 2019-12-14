package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.entity.player.model.events.PlayerOption
import worlds.gregs.hestia.core.script.dsl.task.PlayerOptions
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.PlayerOptionMessage

class PlayerOptionMessageHandler : MessageHandlerSystem<PlayerOptionMessage>() {

    private lateinit var es: EventSystem

    private val logger = LoggerFactory.getLogger(PlayerOptionMessageHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: PlayerOptionMessage) {
        val (playerIndex, option) = message

        //Find player
        val viewport = viewportMapper.get(entityId)
        val playerId = viewport.localPlayers().getEntity(playerIndex)
        if(playerId == -1) {
            return logger.warn("Invalid player index $message")
        }

        //Find option
        val choice = PlayerOptions.values().firstOrNull { it.slot == option } ?: PlayerOptions.values().firstOrNull { it.response != -1 && it.response == option }
        ?: return logger.warn("Cannot find player option $message")

        es.dispatch(PlayerOption(entityId, playerId, choice))
    }
}