package worlds.gregs.hestia.core.action.model

import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Cancellable
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.core.action.logic.dispatch
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.container.api.Composition
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerTransformBuilder
import worlds.gregs.hestia.core.entity.item.container.logic.transform
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

abstract class EntityAction : WorldAction(), Cancellable {
    var cancel = false

    override fun setCancelled(value: Boolean) {
        cancel = value
    }

    override fun isCancelled(): Boolean {
        return cancel
    }

    var entity: Int = -1

    /**
     * Ideal solution here would be `infix fun (Int, Task).awaitPerform(action: A)`
     * Using [Compound extensions](https://github.com/Kotlin/KEEP/pull/176)
     */
    suspend infix fun <T, A> Await.perform(action: A) where A : EntityAction, A : TaskType<T> = suspendCancellableCoroutine<T> {
        action.continuation = it
        task.suspension = action
        entity.perform(action)
    }

    data class Await(val entity: Int, val task: Task)

    infix fun Int.await(task: Task) = Await(entity, task)

    infix fun Int.perform(action: EntityAction) {
        action.entity = this
        super.perform(action)
    }

    override fun perform(action: Action) {
        if (action is EntityAction) {
            action.entity = entity
        }
        super.perform(action)
    }

    /**
     * Workarounds for sending tasks cleanly as [EventListenerBuilder] [then] methods
     * [Compound extensions](https://github.com/Kotlin/KEEP/pull/176) would be the proper solution
     */
    fun queue(priority: Int = 0, action: SuspendableQueue) {
        entity perform task(priority, action)
    }

    fun strongQueue(priority: Int = 1, action: SuspendableQueue) {
        entity perform strongTask(priority, action)
    }

    suspend fun Task.dialogue(block: suspend Dialogue.() -> Unit) {
        onCancel { entity perform CloseDialogue() }
        block(Dialogue(this@EntityAction, this))
        entity perform CloseDialogue()
    }

    /*
        Containers
     */

    infix fun Int.overflow(overflow: Boolean) = ContainerTransformBuilder(overflow)

    infix fun Int.inventory(f: Composition) = inventory() transform f
    fun Int.inventory() = this get Inventory::class

    fun Item.definition(): ItemDefinition {
        val system = world system ItemDefinitionSystem::class
        return system.get(type)
    }

    infix fun Inventory.transform(f: Composition) = ContainerTransformBuilder().transform(f)

    infix fun ContainerTransformBuilder.transform(f: Composition) = transform(apply { function = f })

    infix fun Int.drop(item: Item) {
        //TODO proper floor item dsl
        val position = this get Position::class
        val displayName = this get DisplayName::class
        val clientIndex = this get ClientIndex::class
        world dispatch CreateFloorItem(item.type, item.amount, position.x, position.y, position.plane, displayName.name, 60, clientIndex.index, 60)
    }

    /**
     * Abstraction of common error messages
     */
    infix fun Int.purchase(f: Composition): Boolean {
        return when (val result = overflow(false).transform(f)) {
            is ItemResult.Success -> true
            is ItemResult.Issue -> {
                entity perform Chat(when (result) {
                    ItemResult.Issue.Invalid -> {
                        log("Issue with purchase. ${(entity get Inventory::class).items.toList()}")
                        "Whoops, looks like something went wrong, please try again."
                    }
                    ItemResult.Issue.Full -> "You don't have enough inventory space."
                    is ItemResult.Issue.Underflow ->
                        "You don't have enough ${system(ItemDefinitionSystem::class).get(result.item.type).name.plural(result.item.amount)}."
                })
                false
            }
            else -> false
        }
    }
}

fun EventSystem.perform(entityId: Int, action: EntityAction) {
    action.entity = entityId
    dispatch(action)
}