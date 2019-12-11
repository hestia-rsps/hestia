package worlds.gregs.hestia.core.display.dialogue.systems.type

import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.display.dialogue.systems.types.StatementDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.statement
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class StatementDialogueTest {

    @RelaxedMockK
    private lateinit var scope: TaskScope

    @Test
    fun `Statement dialogue sets data and suspends`() = runBlocking {
        //When
        scope.statement("Text", "Title")
        //Then
        coVerifySequence {
            scope.deferral = StatementDialogue(listOf("Text"), "Title")
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Statement dialogue 6 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            runBlocking {
                scope.statement(lines(6))
            }
        }
    }

    @Test
    fun `Statement dialogue 5 lines fine`() {
        //Then
        assertDoesNotThrow {
            runBlocking {
                scope.statement(lines(5))
            }
        }
    }

    private fun lines(count: Int) = (0..45 * count).mapIndexed { index, _ -> if(index % 2 == 0) "a" else " "}.joinToString(separator = "")

}