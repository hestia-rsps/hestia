package worlds.gregs.hestia.game.queue

import kotlinx.coroutines.cancel
import java.util.*
import kotlin.coroutines.*

class SuspendingCoroutine<T : QueueContext>(override val context: CoroutineContext, block: suspend QueueScope<T>.() -> Unit, override val ctx: T) : Continuation<Unit>, QueueScope<T> {

    internal enum class State { INITIAL, COMPUTING, END }

    internal var state: State = State.INITIAL
    internal var nextStep: Continuation<Unit>? = block.createCoroutine(receiver = this, completion = this)
    var computeContinuation: Continuation<T>? = null

    /**
     * @return Whether the coroutine has computed the last step.
     */
    fun ended() = nextStep == null

    override suspend fun suspend() = suspendCoroutine<Unit> { c ->
        nextStep = c
        resumeIterator()
    }

    override fun end() {
        context.cancel()
        nextStep = null
        state = State.END
    }

    /**
     * Continues the dialogue
     * @return Generic optional response
     */
    suspend fun next(): T {
        return when (state) {
            State.INITIAL -> suspendCoroutine { c ->
                state = State.COMPUTING
                computeContinuation = c
                nextStep!!.resume(Unit)
                ctx
            }
            State.END -> throw NoSuchElementException()
            else -> throw IllegalStateException("Recursive dependency detected -- already computing next")
        }
    }

    protected fun resumeIterator() {
        when (state) {
            State.COMPUTING -> {
                state = State.INITIAL
                computeContinuation?.resume(ctx)
            }
            else -> throw IllegalStateException("Was not supposed to be computing next value. Spurious yield?")
        }
    }

    // Completion continuation implementation
    override fun resumeWith(result: Result<Unit>) {
        nextStep = null
        result
                .onSuccess {
                    resumeIterator()
                }
                .onFailure { exception ->
                    state = State.END
                    computeContinuation!!.resumeWithException(exception)
                }
    }

}