package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.item.container.model.events.ItemOnMob
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.InterfaceOnMob

class InterfaceOnMobHandler : MessageHandlerSystem<InterfaceOnMob>() {

    private lateinit var es: EventSystem

    private lateinit var inventoryMapper: ComponentMapper<Inventory>
    private val logger = LoggerFactory.getLogger(InterfaceOnMobHandler::class.java)!!
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var interfaces: Interfaces

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: InterfaceOnMob) {
        val (slot, type, mobIndex, hash, _) = message
        val inventory = inventoryMapper.get(entityId) ?: return logger.warn("Unhandled interface on mob $message")

        if(!interfaces.hasInterface(entityId, hash)) {
            return logger.warn("Invalid interface on mob hash $message")
        }

        val inventoryItem = inventory.items.getOrNull(slot)

        if(inventoryItem == null || inventoryItem.type != type) {
            return logger.warn("Invalid interface on mob item $message")
        }

        //Find mob
        val viewport = viewportMapper.get(entityId)
        val mobId = viewport.localMobs().getEntity(mobIndex)
        if(mobId == -1) {
            return logger.warn("Invalid interface on mob message $message")
        }

        es.perform(entityId, ItemOnMob(mobId, slot, type))
    }
}