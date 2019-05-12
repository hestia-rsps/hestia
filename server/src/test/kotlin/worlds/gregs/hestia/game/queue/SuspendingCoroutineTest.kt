package worlds.gregs.hestia.game.queue

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

internal class SuspendingCoroutineTest {


    lateinit var context: QueueContext
    lateinit var coroutine: SuspendingCoroutine<QueueContext>
    lateinit var continuation: Continuation<Unit>
    lateinit var scope: suspend QueueScope<QueueContext>.() -> Unit

    @BeforeEach
    fun setup() {
        continuation = mock()
        scope = mock()
        context = mock()
        coroutine = SuspendingCoroutine(EmptyCoroutineContext, scope, context)
        coroutine.nextStep = continuation
    }

    @Test
    fun `Starting state is initial`() {
        //Then
        assertEquals(coroutine.state, SuspendingCoroutine.State.INITIAL)
        assertNull(coroutine.computeContinuation)
    }

    @Test
    fun `Next suspends`() {
        //Given
        coroutine.state = SuspendingCoroutine.State.INITIAL
        whenever(continuation.resume(eq(Unit))).then {
            assertEquals(coroutine.state, SuspendingCoroutine.State.COMPUTING)
            coroutine.resumeWith(Result.success(Unit))
        }
        //When
        runBlocking {
            coroutine.next()
        }
        //Then
        assertEquals(coroutine.state, SuspendingCoroutine.State.INITIAL)
        Assertions.assertNotNull(coroutine.computeContinuation)
        verify(continuation).resume(eq(Unit))
    }

    @Test
    fun `Next failure cancels`() {
        //Given
        coroutine.state = SuspendingCoroutine.State.INITIAL
        whenever(continuation.resume(eq(Unit))).then {
            assertEquals(coroutine.state, SuspendingCoroutine.State.COMPUTING)
            coroutine.resumeWithException(CancellationException("Cancelled"))
        }
        //When
        assertThrows<CancellationException> {
            runBlocking {
                coroutine.next()
            }
        }
        //Then
        assertEquals(coroutine.state, SuspendingCoroutine.State.END)
        Assertions.assertTrue(coroutine.ended())
        verify(continuation).resume(eq(Unit))
    }

    @Test
    fun `End cancels`() {
        //Given
        coroutine.state = SuspendingCoroutine.State.INITIAL
        whenever(continuation.resume(eq(Unit))).then {
            coroutine.resumeWith(Result.success(Unit))
        }
        //When
        coroutine.end()
        //Then
        assertEquals(SuspendingCoroutine.State.END, coroutine.state)
        assertNull(coroutine.nextStep)
        assert(coroutine.ended())
    }
}