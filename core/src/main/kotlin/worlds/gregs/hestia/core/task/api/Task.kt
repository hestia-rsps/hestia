package worlds.gregs.hestia.core.task.api

import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.DialogueBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.options
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.statement
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem
import kotlin.coroutines.Continuation

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

    fun cancel(cancellation: TaskCancellation) {
        throw cancellation
    }

    fun cancel(message: String) = cancel(TaskCancellation.Cancellation(message))

    suspend fun <T> EntityAction.await(type: TaskType<T>) = suspendCancellableCoroutine<T> {
        type.continuation = it
        suspension = type
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

/*
    Interaction
 */

    fun Action.canInteract(route: worlds.gregs.hestia.core.task.logic.systems.Route, position: Position, targetPosition: Position, targetEntityId: Int): Boolean {
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