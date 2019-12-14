package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.widget.api.UserInterface
import worlds.gregs.hestia.core.entity.item.container.logic.InventorySystem
import worlds.gregs.hestia.core.entity.item.container.logic.switch
import worlds.gregs.hestia.core.entity.item.container.logic.transform
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WidgetSwitchComponent
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class WidgetSwitchComponentHandler : MessageHandlerSystem<WidgetSwitchComponent>() {

    private lateinit var es: EventSystem

    private lateinit var inventoryMapper: ComponentMapper<Inventory>
    private val logger = LoggerFactory.getLogger(WidgetSwitchComponentHandler::class.java)!!
    private lateinit var definitions: ItemDefinitionSystem
    private lateinit var inventorySystem: InventorySystem
    private lateinit var ui: UserInterface

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WidgetSwitchComponent) {
        val (fromType, fromIndex, toType, fromHash, toIndex, toHash) = message
        val inventory = inventoryMapper.get(entityId) ?: return logger.warn("Unhandled widget component switch $message")

        if(!ui.validate(entityId, fromHash) || !ui.validate(entityId, toHash)) {
            return logger.warn("Invalid widget component switch hash $message")
        }

        //TODO this is for inventory only, with interface hash checks, this should be an event and handled by interface system.
        val fromSlot = if(fromIndex >= 28) fromIndex - 28 else fromIndex
        val toSlot = if(toIndex >= 28) toIndex - 28 else toIndex

        println("Switch $fromIndex $toIndex -> $fromSlot $toSlot")

        val fromItem = inventory.items.getOrNull(fromSlot)
        val toItem = inventory.items.getOrNull(toSlot)

        //fromType appears to always be -1, possibly to signify the one being dragged?
        if(fromItem != null && fromType != -1 && fromItem.type != toType || toItem != null && toType != -1 && toItem.type != toType) {
            return logger.warn("Invalid widget component switch items $message")
        }

        println(inventory.transform(definitions.reader, inventorySystem.stackType, switch(fromSlot, toSlot)))//Result is ignored regardless
    }
}