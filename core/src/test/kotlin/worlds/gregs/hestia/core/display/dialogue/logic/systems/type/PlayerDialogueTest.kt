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
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.PlayerDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.player
import worlds.gregs.hestia.core.task.api.Task
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class PlayerDialogueTest : LinesDialogueTest {

    @RelaxedMockK
    private lateinit var task: Task
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    fun setup() {
        every { task.suspension = any() } propertyType PlayerDialogue::class answers {
            continuation = arg<PlayerDialogue>(0).continuation
            continuation.resume(Unit)
        }
    }

    @Test
    fun `Player dialogue sets data and suspends`() = runBlocking {
        //When
        task.player("Text", 5, "Title")
        //Then
        coVerifySequence {
            task.suspension = PlayerDialogue(listOf("Text"), "Title", 5, continuation)
        }
        confirmVerified(task)
    }

    @Test
    fun `Player dialogue 5 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                task.player(lines(5))
            }
        }
    }

    @Test
    fun `Player dialogue 4 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                task.player(lines(4))
            }
        }
    }

}