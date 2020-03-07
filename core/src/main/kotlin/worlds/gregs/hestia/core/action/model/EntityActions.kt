package worlds.gregs.hestia.core.action.model

import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.action.logic.dispatch
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseWindow
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.item.container.api.Composition
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerSystem
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerTransformBuilder
import worlds.gregs.hestia.core.entity.item.container.logic.transform
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskSuspension
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.await.ClearTasks
import worlds.gregs.hestia.core.task.model.events.StartTask
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

interface EntityActions : WorldActions {

    var entity: Int

    /**
     * Ideal solution here would be `infix fun (Int, Task).awaitPerform(action: A)`
     * Using [Compound extensions](https://github.com/Kotlin/KEEP/pull/176)
     */
    suspend infix fun <T, A> Await.perform(action: A) where A : EntityAction, A : TaskSuspension<T> = suspendCancellableCoroutine<T> {
        action.continuation = it
        task.suspension = action
        entity.perform(action)
    }

    override fun perform(action: Event) {
        if (action is EntityAction) {
            action.entity = entity
        }
        super.perform(action)
    }

    data class Await(val entity: Int, val task: Task)

    infix fun Int.await(task: Task) = Await(entity, task)

    infix fun Int.perform(action: EntityAction) {
        action.entity = this
        val es = world.getSystem(EventSystem::class)
        es.perform(entity, action)
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
        onCancel { entity perform CloseWindow(Window.DIALOGUE_BOX) }
        block(Dialogue(this@EntityActions, this))
        entity perform CloseWindow(Window.DIALOGUE_BOX)
    }

    /*
        Containers
     */

    infix fun Int.container(type: ContainerType) = system(ContainerSystem::class).getContainer(this, type)

    infix fun Int.overflow(overflow: Boolean) = ContainerTransformBuilder(overflow)

    infix fun Int.inventory(f: Composition) = ContainerType.INVENTORY transform f

    fun Item.definition(): ItemDefinition {
        val system = world system ItemDefinitionSystem::class
        return system.get(type)
    }

    infix fun ContainerType.transform(f: Composition) = ContainerTransformBuilder().apply { this.type = this@transform }.transform(f)

    infix fun ContainerTransformBuilder.transform(f: Composition) = transform(apply { function = f })

    fun strongTask(priority: Int = 1, queue: SuspendableQueue): EntityAction {
        return StartTask(InactiveTask(priority) {
            suspendCancellableCoroutine<Unit> {
                val clear = ClearTasks(priority)
                clear.continuation = it
                suspension = clear
                val es = world.getSystem(EventSystem::class)
                es.perform(entity, clear)
            }
            queue(this)
        })
    }

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
                        log("Issue with purchase. ${(entity container ContainerType.INVENTORY).toList()}")
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