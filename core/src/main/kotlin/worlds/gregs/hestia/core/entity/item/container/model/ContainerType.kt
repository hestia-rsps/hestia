package worlds.gregs.hestia.core.entity.item.container.model

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Inventory
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WornEquipment
import worlds.gregs.hestia.core.entity.item.container.api.Container

enum class ContainerType(val interfaceId: Int, val containerId: Int, val type: StackType = StackType.Normal) {
    INVENTORY(Inventory, 93),
    EQUIPMENT(WornEquipment, 94);
}