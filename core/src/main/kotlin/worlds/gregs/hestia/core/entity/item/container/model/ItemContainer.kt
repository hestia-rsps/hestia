package worlds.gregs.hestia.core.entity.item.container.model

import com.artemis.Component
import worlds.gregs.hestia.core.entity.item.container.api.Container

abstract class ItemContainer : Component() {
    abstract val items: Container
    abstract var refresh: Boolean
    internal abstract fun setItems(items: Container): Boolean
}