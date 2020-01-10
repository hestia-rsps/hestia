package worlds.gregs.hestia.core.entity.item.container.logic

import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import worlds.gregs.hestia.core.task.api.TaskCancellation
import kotlin.reflect.KClass

abstract class ContainerSystem(container: KClass<out ItemContainer>) : IteratingSystem(Aspect.all(NetworkSession::class, container)) {

    abstract val stackType: StackType

    override fun process(entityId: Int) {
        val container = getContainer(entityId)
        if(container.refresh) {
            update(entityId)
        }
    }

    abstract fun update(entityId: Int)

    abstract fun getContainer(entityId: Int): ItemContainer
}

/**
 * Checks the parameters match the values in the container
 */
fun ItemContainer.validateItem(slot: Int, type: Int): Item {
    val item = items.getOrNull(slot)
    if(item == null) {
        throw TaskCancellation.Cancellation("Null item container item $slot $type")
    } else if(item.type != type) {
        throw TaskCancellation.Cancellation("Invalid item container item $slot $type - $item")
    }
    return item
}

/**
 * Checks the slot value isn't empty
 */
fun ItemContainer.validateSlot(slot: Int): Item {
    return items.getOrNull(slot) ?: throw TaskCancellation.Cancellation("Null item container slot $slot")
}
