package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.logic.systems.InterfaceOptionSystem
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceId
import worlds.gregs.hestia.core.action.model.*
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOptionMessage
import worlds.gregs.hestia.service.cache.definition.systems.InterfaceDefinitionSystem

class InterfaceOptionHandler : MessageHandlerSystem<InterfaceOptionMessage>() {

    private lateinit var options: InterfaceOptionSystem
    private lateinit var interfaces: Interfaces
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>
    private val logger = LoggerFactory.getLogger(InterfaceOptionHandler::class.java)!!

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceOptionMessage) {
        val (hash, fromSlot, toSlot, option) = message

        if (interfaces.verify(entityId, hash)) {
            val id = getInterfaceId(hash)
            val options = options.get(option, hash)?: return logger.warn("Invalid interface action $id $option")
            val actionContext = actionContextMapper.get(entityId)
            options.invoke(actionContext, hash, fromSlot, toSlot, option)
        }
    }

}