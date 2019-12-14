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
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.ItemDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.item
import worlds.gregs.hestia.core.task.api.Task
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class ItemDialogueTest : LinesDialogueTest {

    @RelaxedMockK
    private lateinit var task: Task
    private lateinit var continuation: CancellableContinuation<Unit>

    @BeforeEach
    fun setup() {
        every { task.suspension = any() } propertyType ItemDialogue::class answers {
            continuation = arg<ItemDialogue>(0).continuation
            continuation.resume(Unit)
        }
    }

    @Test
    fun `Item dialogue sets data and suspends`() = runBlocking {
        //When
        task.item("Text", 5, "Title")
        //Then
        coVerifySequence {
            task.suspension = ItemDialogue(listOf("Text"), "Title", 5, continuation)
        }
        confirmVerified(task)
    }

    @Test
    fun `Item dialogue 5 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                task.item(lines(5))
            }
        }
    }

    @Test
    fun `Item dialogue 4 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                task.item(lines(4))
            }
        }
    }

}