package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.container.model.events.ItemOnNpc
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnNpc

class InterfaceOnNpcHandler : MessageHandlerSystem<InterfaceOnNpc>() {

    private lateinit var es: EventSystem

    private lateinit var containerSystem: ContainerSystem
    private val logger = LoggerFactory.getLogger(InterfaceOnNpcHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var interfaces: Interfaces

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceOnNpc) {
        val (slot, type, npcIndex, hash, _) = message

        if(!interfaces.hasInterface(entityId, hash)) {
            return logger.warn("Invalid interface on npc hash $message")
        }

        val container = containerSystem.getContainer(entityId, hash shr 16) ?: return logger.warn("Unhandled interface on npc $message")
        val item = container.getOrNull(slot)

        if(item == null || item.type != type) {
            return logger.warn("Invalid interface on npc item $message")
        }

        //Find npc
        val viewport = viewportMapper.get(entityId)
        val npcId = viewport.localNpcs().getEntity(npcIndex)
        if(npcId == -1) {
            return logger.warn("Invalid interface on npc message $message")
        }

        es.perform(entityId, ItemOnNpc(npcId, slot, type))
    }
}