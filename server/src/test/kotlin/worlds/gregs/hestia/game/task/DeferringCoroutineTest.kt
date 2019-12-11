package worlds.gregs.hestia.game.task

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class DeferringCoroutineTest {

    lateinit var coroutine: DeferringCoroutine
    lateinit var continuation: Continuation<Unit>
    lateinit var scope: suspend TaskScope.() -> Unit

    @BeforeEach
    fun setup() {
        continuation = mock()
        scope = mock()
        coroutine = DeferringCoroutine(EmptyCoroutineContext, scope, TaskPriority.Weak)
        coroutine.nextStep = continuation
    }

    @Test
    fun `Starting state is initial`() {
        //Then
        assertEquals(coroutine.state, DeferringCoroutine.State.INITIAL)
        assertNull(coroutine.computeContinuation)
    }

    @Test
    fun `Next suspends`() {
        //Given
        coroutine.state = DeferringCoroutine.State.INITIAL
        whenever(continuation.resume(eq(Unit))).then {
            assertEquals(coroutine.state, DeferringCoroutine.State.COMPUTING)
            coroutine.resumeWith(Result.success(Unit))
        }
        //When
        runBlocking {
            coroutine.next()
        }
        //Then
        assertEquals(coroutine.state, DeferringCoroutine.State.INITIAL)
        Assertions.assertNotNull(coroutine.computeContinuation)
        verify(continuation).resume(eq(Unit))
    }

    @Test
    fun `Next failure cancels`() {
        //Given
        coroutine.state = DeferringCoroutine.State.INITIAL
        whenever(continuation.resume(eq(Unit))).then {
            assertEquals(coroutine.state, DeferringCoroutine.State.COMPUTING)
            coroutine.resumeWithException(CancellationException("Cancelled"))
        }
        //When
        assertThrows<CancellationException> {
            runBlocking {
                coroutine.next()
            }
        }
        //Then
        assertEquals(coroutine.state, DeferringCoroutine.State.END)
        Assertions.assertTrue(coroutine.stopped())
        verify(continuation).resume(eq(Unit))
    }

    @Test
    fun `End cancels`() = runBlocking {
        //Given
        coroutine.state = DeferringCoroutine.State.INITIAL
        whenever(continuation.resume(eq(Unit))).then {
            coroutine.resumeWith(Result.success(Unit))
        }
        //When
        coroutine.stop(false)
        //Then
        assertEquals(DeferringCoroutine.State.END, coroutine.state)
        assertNull(coroutine.nextStep)
        assert(coroutine.stopped())
    }
}