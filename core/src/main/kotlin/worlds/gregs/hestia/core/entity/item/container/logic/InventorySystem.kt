package worlds.gregs.hestia.core.entity.item.container.logic

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.entity.item.container.api.Composition
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItems
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class InventorySystem : ContainerSystem(Inventory::class) {

    override val stackType = StackType.Normal
    private lateinit var inventoryMapper: ComponentMapper<Inventory>
    private lateinit var es: EventSystem
    private lateinit var definitions: ItemDefinitionSystem

    override fun update(entityId: Int) {
//        tab.open(entityId)
        val container = inventoryMapper.get(entityId)
        es.send(entityId, InterfaceItems(93, container.items.map { if(it == null) Pair(-1, 0) else Pair(it.type, it.amount)}))
    }

    override fun getContainer(entityId: Int): ItemContainer {
        return inventoryMapper.get(entityId)
    }

    fun transform(entityId: Int, f: Composition, overflow: Boolean = true): ItemResult {
        val inventory = inventoryMapper.get(entityId)
        return inventory.transform(definitions.reader, stackType, f, overflow)
    }
}