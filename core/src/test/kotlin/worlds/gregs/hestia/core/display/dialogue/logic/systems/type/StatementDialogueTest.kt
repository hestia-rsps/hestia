package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.display.dialogue.logic.systems.LinesDialogueTest
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StatementDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.statement
import worlds.gregs.hestia.core.task.api.Task
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class StatementDialogueTest : LinesDialogueTest {

    @RelaxedMockK
    private lateinit var task: Task
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    fun setup() {
        every { task.suspension = any() } propertyType StatementDialogue::class answers {
            continuation = arg<StatementDialogue>(0).continuation
            continuation.resume(Unit)
        }
    }

    @Test
    fun `Statement dialogue sets data and suspends`() = runBlocking {
        //When
        task.statement("Text", "Title")
        //Then
        coVerifySequence {
            task.suspension = StatementDialogue(listOf("Text"), "Title", continuation)
        }
        confirmVerified(task)
    }

    @Test
    fun `Statement dialogue 6 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                task.statement(lines(6))
            }
        }
    }

    @Test
    fun `Statement dialogue 5 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                task.statement(lines(5))
            }
        }
    }

}