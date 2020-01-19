package worlds.gregs.hestia.core.display.interfaces.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions
import worlds.gregs.hestia.core.display.request.model.components.ContextMenu

abstract class ContextMenus : SubscriptionSystem(Aspect.all(ContextMenu::class)) {

    sealed class ContextMenuResult {
        object Success : ContextMenuResult()
        object SlotInUse : ContextMenuResult()
    }

    abstract fun removeOption(entityId: Int, option: PlayerOptions)

    abstract fun setOption(entityId: Int, option: PlayerOptions): ContextMenuResult

    abstract fun hasOption(entityId: Int, option: PlayerOptions): Boolean
}