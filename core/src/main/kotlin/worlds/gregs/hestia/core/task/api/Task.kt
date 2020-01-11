package worlds.gregs.hestia.core.task.api

import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.DialogueBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.options
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.statement
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

    suspend fun <T> await(type: TaskType<T>) = suspendCancellableCoroutine<T> {
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


    companion object {
        const val FIRST = 0
        const val SECOND = 1
        const val THIRD = 2
        const val FOURTH = 3
        const val FIFTH = 4
        val SUCCESS = null
    }
}