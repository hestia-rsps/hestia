package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.PlayerDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.player
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class PlayerDialogueTest {

    @RelaxedMockK
    private lateinit var scope: TaskScope

    @Test
    fun `Player dialogue sets data and suspends`() = runBlocking {
        //When
        scope.player("Text", 5,  "Title")
        //Then
        coVerifySequence {
            scope.deferral = PlayerDialogue(listOf("Text"), "Title", 5)
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Player dialogue 5 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                scope.player(lines(5))
            }
        }
    }

    @Test
    fun `Player dialogue 4 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                scope.player(lines(4))
            }
        }
    }

    private fun lines(count: Int) = (0..45 * count).mapIndexed { index, _ -> if(index % 2 == 0) "a" else " "}.joinToString(separator = "")

}