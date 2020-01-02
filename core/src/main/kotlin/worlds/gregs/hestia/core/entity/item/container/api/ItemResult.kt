package worlds.gregs.hestia.core.entity.item.container.api

import worlds.gregs.hestia.core.entity.item.container.model.Item

sealed class ItemResult {
    sealed class Success : ItemResult() {
        object Success: ItemResult.Success()
        data class Overflow(val list: List<Item>) : ItemResult.Success()
    }
    sealed class Issue : ItemResult() {
        object Invalid : Issue()
        object Full : Issue()
        data class Underflow(val item: Item) : Issue()
    }
}