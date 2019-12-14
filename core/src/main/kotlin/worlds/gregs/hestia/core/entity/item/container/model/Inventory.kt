package worlds.gregs.hestia.core.entity.item.container.model

import worlds.gregs.hestia.core.entity.item.container.api.Container

class Inventory : ItemContainer() {
    override var items = arrayOfNulls<Item>(28)
        private set
    override var refresh = false

    override fun setItems(items: Container): Boolean {
        if(items.size != this.items.size) {
            return false
        }
        this.items = items
        refresh = true
        return true
    }
}