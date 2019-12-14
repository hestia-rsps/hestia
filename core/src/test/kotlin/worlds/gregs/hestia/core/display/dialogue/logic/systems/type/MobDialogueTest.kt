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
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.MobDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.mob
import worlds.gregs.hestia.core.task.api.Task
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class MobDialogueTest : LinesDialogueTest {

    @RelaxedMockK
    private lateinit var task: Task
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    fun setup() {
        every { task.suspension = any() } propertyType MobDialogue::class answers {
            continuation = arg<MobDialogue>(0).continuation
            continuation.resume(Unit)
        }
    }

    @Test
    fun `Mob dialogue sets data and suspends`() = runBlocking {
        //When
        task.mob("Text", 5, 10, "Title")
        //Then
        coVerifySequence {
            task.suspension = MobDialogue(listOf("Text"), "Title", 5, 10, continuation)
        }
        confirmVerified(task)
    }

    @Test
    fun `Mob dialogue 5 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                task.mob(lines(5))
            }
        }
    }

    @Test
    fun `Mob dialogue 4 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                task.mob(lines(4))
            }
        }
    }

}