package worlds.gregs.hestia.core.plugins.dialogue.systems.type

import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.ItemDialogue
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.item
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class ItemDialogueTest {

    @RelaxedMockK
    private lateinit var scope: TaskScope

    @Test
    fun `Item dialogue sets data and suspends`() = runBlocking {
        //When
        scope.item("Text", 5, "Title")
        //Then
        coVerifySequence {
            scope.deferral = ItemDialogue(listOf("Text"), "Title", 5)
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Item dialogue 5 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                scope.item(lines(5))
            }
        }
    }

    @Test
    fun `Item dialogue 4 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                scope.item(lines(4))
            }
        }
    }

    private fun lines(count: Int) = (0..45 * count).mapIndexed { index, _ -> if(index % 2 == 0) "a" else " "}.joinToString(separator = "")
}