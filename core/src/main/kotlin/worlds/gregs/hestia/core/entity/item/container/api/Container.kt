package worlds.gregs.hestia.core.entity.item.container.api

import arrow.core.Either
import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import worlds.gregs.hestia.core.task.api.TaskCancellation

data class ContainerModificationDetails(val either: Either<ItemResult.Issue, Container>, val definitions: DefinitionReader<ItemDefinition>, val overflows: MutableList<Item>, val stackType: StackType)
typealias Composition = (ContainerModificationDetails) -> ContainerModificationDetails
typealias Container = Array<Slot>
typealias Slot = Item?

/**
 * Checks the parameters match the values in the container
 */
fun Container.validateItem(slot: Int, type: Int): Item? {
    val item = getOrNull(slot)
    if (item == null && type != -1) {
        throw TaskCancellation.Cancellation("Null item container item $slot $type")
    } else if (item != null && item.type != type) {
        throw TaskCancellation.Cancellation("Invalid item container item $slot $type - $item")
    }
    return item
}

/**
 * Checks the slot value isn't empty
 */
fun Container.validateSlot(slot: Int): Item {
    return getOrNull(slot) ?: throw TaskCancellation.Cancellation("Null item container slot $slot")
}
