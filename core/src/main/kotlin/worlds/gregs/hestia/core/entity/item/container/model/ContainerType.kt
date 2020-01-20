package worlds.gregs.hestia.core.entity.item.container.model

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Inventory

enum class ContainerType(val interfaceId: Int, val containerId: Int, val type: StackType = StackType.Normal) {
    INVENTORY(Inventory, 93)
}