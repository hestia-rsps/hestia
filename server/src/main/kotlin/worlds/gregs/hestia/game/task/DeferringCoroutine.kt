package worlds.gregs.hestia.game.task

import kotlinx.coroutines.cancel
import java.util.*
import kotlin.coroutines.*

class DeferringCoroutine(override val context: CoroutineContext, block: suspend TaskScope.() -> Unit, override val priority: TaskPriority) : Continuation<Unit>, TaskScope {

    internal enum class State { INITIAL, COMPUTING, END }

    internal var state: State = State.INITIAL
    internal var nextStep: Continuation<Unit>? = block.createCoroutine(receiver = this, completion = this)
    var computeContinuation: Continuation<DeferralType?>? = null
    override var deferral: DeferralType? = null

    override fun stopped() = nextStep == null

    override suspend fun defer() = suspendCoroutine<Unit> { c ->
        nextStep = c
        resumeIterator()
    }

    override suspend fun stop(block: Boolean) {
        context.cancel()
        nextStep = null
        state = State.END
        if(block) {
            defer()
        }
    }

    override suspend fun next(): DeferralType? {
        return when (state) {
            State.INITIAL -> suspendCoroutine { c ->
                state = State.COMPUTING
                computeContinuation = c
                nextStep!!.resume(Unit)
                deferral
            }
            State.END -> throw NoSuchElementException()
            else -> throw IllegalStateException("Recursive dependency detected -- already computing next")
        }
    }

    private fun resumeIterator() {
        when (state) {
            State.COMPUTING -> {
                state = State.INITIAL
                computeContinuation?.resume(deferral)
            }
            else -> throw IllegalStateException("Was not supposed to be computing next value. Spurious yield?")
        }
    }

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