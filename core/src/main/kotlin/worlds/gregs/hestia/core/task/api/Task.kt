package worlds.gregs.hestia.core.task.api

import com.artemis.BaseSystem
import com.artemis.Component
import com.artemis.ComponentMapper
import kotlinx.coroutines.CompletionHandler
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.DialogueBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.options
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.statement
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer
import worlds.gregs.hestia.core.task.logic.systems.TaskSystem
import worlds.gregs.hestia.core.task.logic.systems.awaitDistance
import worlds.gregs.hestia.core.task.logic.systems.awaitRoute
import worlds.gregs.hestia.core.task.logic.systems.wait
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem
import worlds.gregs.hestia.core.world.movement.model.components.calc.Follow
import worlds.gregs.hestia.core.world.movement.model.components.calc.Route
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

interface Task : Continuation<Any> {
    override val context: TaskContext

    var suspension: TaskType<*>?

    fun onCancel(handler: CompletionHandler)

    val isActive: Boolean
        get() = suspension?.continuation?.isActive ?: false

    val isCancelled: Boolean
        get() = suspension?.continuation?.isCancelled ?: false

    val isCompleted: Boolean
        get() = suspension?.continuation?.isCompleted ?: true


    /*
        Extension infix's would require [compound extensions](https://github.com/Kotlin/KEEP/pull/176/commits).
     */

    suspend fun cancel(cancellation: TaskCancellation) = suspendCoroutine<Unit> {
        val tasks = system(TaskSystem::class)
        tasks.cancel(entity, cancellation)
    }

    suspend fun cancel(message: String) = cancel(TaskCancellation.Cancellation(message))

    infix fun <T : Component> Int.get(c: KClass<T>) = map(c).get(this)

    infix fun <T : Component> Int.getUnsafe(c: KClass<T>): T? = map(c).get(this)

    infix fun <T : Component> Int.create(c: KClass<T>) = map(c).create(this)

    infix fun <T : Component> Int.remove(c: KClass<T>) = map(c).remove(this)

    infix fun <T : Component> Int.has(c: KClass<T>) = map(c).has(this)

    infix fun <T : BaseSystem> system(c: KClass<T>) = world.getSystem(c.java)

    fun <T : Component> map(c: KClass<T>): ComponentMapper<T> = world.getMapper(c.java)

    infix fun Int.send(message: Message) {
        system(EventSystem::class).send(this, message)
    }


    /*
        Dialogue
     */
    infix fun Int.animation(animation: Int) = DialogueBuilder(target = this, animation = animation)

    infix fun DialogueBuilder.animation(animation: Int) = apply { this.animation = animation }

    infix fun Int.title(title: String) = DialogueBuilder(target = this, title = title)

    infix fun DialogueBuilder.title(title: String) = apply { this.title = title }

    suspend infix fun Int.dialogue(message: String) = DialogueBuilder(target = this).dialogue(message)

    suspend infix fun DialogueBuilder.dialogue(message: String) = dialogue(apply { this.message = message })

    suspend infix fun Int.statement(message: String) = DialogueBuilder(target = this).statement(message)

    suspend infix fun DialogueBuilder.statement(message: String) = statement(message, title)

    suspend infix fun Int.options(message: String) = DialogueBuilder(target = this).options(message)

    suspend infix fun DialogueBuilder.options(message: String) = options(apply { this.message = message })


    /**
     * Checks the parameters match the values in the container
     */
    suspend fun ItemContainer.validateItem(slot: Int, type: Int): Item? {
        val item = items.getOrNull(slot)
        if (item == null && type != -1 || item != null && item.type != type) {
            cancel("Invalid item container item $slot $type - $item")
            return null
        }
        return item
    }

    /**
     * Checks the slot value isn't empty
     */
    suspend fun ItemContainer.validateSlot(slot: Int): Item {
        val item = items.getOrNull(slot)
        if (item == null) {
            cancel("Invalid item container slot $slot - $item")
        }
        return item!!
    }
/*
    Interaction
 */


    /**
     * Checks static entity is valid, creates route, waits for movement and returns if reached entity
     */
    suspend fun Int.interact(target: Int, distance: Int) {
        onCancel {
            val position = target.get(Position::class)
            entity.get(Face::class).apply { x = position.x; y = position.y }
            entity.create(Facing::class)
            world.getSystem(EventSystem::class).perform(entity, Chat("You can't reach that."))
        }

        val position = entity get Position::class
        val targetPosition = target getUnsafe Position::class

        //Check valid entity
        if (!world.entityManager.isActive(target) || targetPosition == null || targetPosition.plane != position.plane) {
            return cancel(TaskCancellation.OutOfReach)
        }

        val result: Boolean

        //If range check, then follow (mob/player)
        if (distance > 0) {
            //Start following
            val follow = entity create Follow::class
            follow.entity = target

            //Wait till within distance
            result = awaitDistance(target, distance)
            //Cancel target
            follow.entity = -1
        } else {
            //Start route
            val route = entity create Route::class
            route.entityId = target
            route.alternative = true

            val actualRoute = awaitRoute()
            wait(1) //The system processes before the client has updated, so we wait 1 tick allowing the client to catch up.
            result = canInteract(actualRoute, position, targetPosition, target)
        }

        //Cancel if not possible
        if (!result) {
            cancel(TaskCancellation.OutOfReach)
        }
    }

    private fun canInteract(route: worlds.gregs.hestia.core.task.logic.systems.Route, position: Position, targetPosition: Position, targetEntityId: Int): Boolean {
        if (route.steps < 0) {
            return false
        }
        val sizeX: Int
        val sizeY: Int

        val gameObject = targetEntityId.getUnsafe(GameObject::class)
        if (gameObject != null) {
            val definitions = system(ObjectDefinitionSystem::class)
            val definition = definitions.get(gameObject.id)
            sizeX = definition.sizeX
            sizeY = definition.sizeY
        } else {
            val sizeMapper = map(Size::class)
            sizeX = sizeMapper.width(targetEntityId)
            sizeY = sizeMapper.height(targetEntityId)
        }

        return !route.alternative || StepBesideSystem.isNear(position.x, position.y, targetPosition.x, targetPosition.y, sizeX, sizeY, true)
    }


    companion object {
        const val FIRST = 0
        const val SECOND = 1
        const val THIRD = 2
        const val FOURTH = 3
        const val FIFTH = 4
        val SUCCESS = null
    }
}