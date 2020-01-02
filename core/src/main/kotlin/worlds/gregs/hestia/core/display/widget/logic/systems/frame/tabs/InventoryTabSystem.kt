package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import com.artemis.ComponentMapper
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.InventoryTab
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.InventorySystem
import worlds.gregs.hestia.core.entity.item.container.logic.remove
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItems
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class InventoryTabSystem : BaseFrame(InventoryTab::class) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var inventoryMapper: ComponentMapper<Inventory>
    private lateinit var definitions: ItemDefinitionSystem
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var inventorySystem: InventorySystem

    override fun open(entityId: Int) {
        super.open(entityId)
        val inventory = inventoryMapper.get(entityId)
        es.send(entityId, WidgetComponentSettings(getId(entityId), 0, 0, 27, 4554126))//Item slots
        es.send(entityId, WidgetComponentSettings(getId(entityId), 0, 28, 55, 2097152))//Draggable slots
        es.send(entityId, WidgetItems(93, inventory.items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}))
    }

    override fun getId(entityId: Int): Int {
        return INV_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        val inventory = inventoryMapper.get(entityId)
        val item = inventory.items.getOrNull(toSlot)

        if(item == null || item.type != fromSlot) {
            return logger.warn("Invalid inventory action $componentId $fromSlot $toSlot $option")
        }
        val definition = definitions.get(item.type)
        val choice = option
        val option = definition.options.getOrNull(choice - 1)

        if(option != null) {
            println("Inventory action $option $fromSlot $toSlot")
        } else {
            when(choice) {
                7 -> {//Drop
                    val result = inventorySystem.transform(entityId, remove(item.type, item.amount, toSlot))
                    if(result is ItemResult.Success) {
                        val position = positionMapper.get(entityId)
                        val displayName = displayNameMapper.get(entityId)
                        val clientIndex = clientIndexMapper.get(entityId)
                        es.dispatch(CreateFloorItem(item.type, item.amount, position.x, position.y, position.plane, displayName.name, 60, clientIndex.index, 60))
                    } else {
                        logger.warn("Issue dropping item ${inventory.items} $item $toSlot")
                    }
                }
                8 -> {//Examine
                }
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(InventoryTabSystem::class.java)!!
        private const val INV_ID = 679
        private const val RESIZABLE_INDEX = 94
        private const val FIXED_INDEX = 208
    }
}