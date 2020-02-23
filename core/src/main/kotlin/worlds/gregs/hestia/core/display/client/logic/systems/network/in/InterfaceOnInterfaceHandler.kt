package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.container.model.events.ItemOnItem
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnInterface

class InterfaceOnInterfaceHandler : MessageHandlerSystem<InterfaceOnInterface>() {

    private lateinit var es: EventSystem


    private lateinit var containerSystem: ContainerSystem
    private val logger = LoggerFactory.getLogger(InterfaceOnInterfaceHandler::class.java)!!
    private lateinit var interfaces: Interfaces

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceOnInterface) {
        val (fromHash, fromType, fromSlot, toHash, toType, toSlot) = message

        if(!interfaces.hasInterface(entityId, fromHash) || !interfaces.hasInterface(entityId, toHash)) {
            return logger.warn("Invalid interface on interface hash $message")
        }
        val fromContainer = containerSystem.getContainer(entityId, fromHash shr 16) ?: return logger.warn("Unhandled interface on interface $message")
        val toContainer = containerSystem.getContainer(entityId, toHash shr 16) ?: return logger.warn("Unhandled interface on interface $message")

        val fromItem = fromContainer.getOrNull(fromSlot)
        val toItem = toContainer.getOrNull(toSlot)

        if(fromItem == null || fromItem.type != fromType || toItem == null || toItem.type != toType) {
            return logger.warn("Invalid interface on interface message $message")
        }

        es.perform(entityId, ItemOnItem(fromHash, fromType, fromSlot, toHash, toType, toSlot))
    }
}