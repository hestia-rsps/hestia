package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.floor.model.events.ItemOnPlayer
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnPlayer

class InterfaceOnPlayerHandler : MessageHandlerSystem<InterfaceOnPlayer>() {

    private lateinit var es: EventSystem

    private lateinit var containerSystem: ContainerSystem
    private val logger = LoggerFactory.getLogger(InterfaceOnPlayerHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var interfaces: Interfaces

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceOnPlayer) {
        val (playerIndex, hash, type, _, slot) = message

        if(!interfaces.hasInterface(entityId, hash)) {
            return logger.warn("Invalid interface on player hash $message")
        }

        val container = containerSystem.getContainer(entityId, hash shr 16) ?: return logger.warn("Unhandled interface on player $message")
        val item = container.getOrNull(slot)

        if(item == null || item.type != type) {
            return logger.warn("Invalid interface on player item $message")
        }

        //Find player
        val viewport = viewportMapper.get(entityId)
        val playerId = viewport.localPlayers().getEntity(playerIndex)
        if(playerId == -1) {
            return logger.warn("Invalid interface on player index $message")
        }

        es.perform(entityId, ItemOnPlayer(playerId, hash, slot, type))
    }
}