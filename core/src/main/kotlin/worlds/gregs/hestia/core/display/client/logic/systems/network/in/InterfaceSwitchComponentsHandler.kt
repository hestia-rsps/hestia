package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.events.ComponentSwitch
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceSwitchComponents

class InterfaceSwitchComponentsHandler : MessageHandlerSystem<InterfaceSwitchComponents>() {

    private val logger = LoggerFactory.getLogger(InterfaceSwitchComponentsHandler::class.java)!!
    private lateinit var interfaces: Interfaces
    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceSwitchComponents) {
        val (fromType, fromIndex, toType, fromHash, toIndex, toHash) = message
        if(interfaces.verify(entityId, fromHash) && interfaces.verify(entityId, toHash)) {
            es.perform(entityId, ComponentSwitch( fromHash shr 16, fromIndex, fromType, toHash shr 16, toIndex, toType))
        } else {
            logger.warn("Invalid interface component switch hash $message")
        }
    }
}