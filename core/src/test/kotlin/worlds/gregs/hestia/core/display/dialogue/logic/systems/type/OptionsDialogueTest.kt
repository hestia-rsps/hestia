package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBoxSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.OptionsDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.OptionsDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.option
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.Script
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class OptionsDialogueTest : MockkGame() {

    @SpyK
    var system = OptionsDialogueSystem()

    @RelaxedMockK
    private lateinit var task: Task
    private lateinit var continuation: CancellableContinuation<Int>
    @RelaxedMockK
    private lateinit var windows: Windows

    @BeforeEach
    override fun setup() {
        super.setup()
        every { task.suspension = any() } propertyType OptionsDialogue::class answers {
            continuation = arg<OptionsDialogue>(0).continuation
            continuation.resume(2)
        }
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, mockk<Tasks>(relaxed = true), DialogueBoxSystem(), windows)
    }

    @Test
    fun `Option dialogue sets and suspends`() = runBlocking {
        //When
        val response = task.option("Title", listOf("Option 1", "Option 2"))
        //Then
        assertEquals(2, response)
        coVerifySequence {
            task.suspension = OptionsDialogue(listOf("Option 1", "Option 2"), "Title", continuation)
        }
        confirmVerified(task)
    }

    @Test
    fun `Suspend process ignores other types`() {
        //Given
        val suspension: TaskType<*> = mockk()
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { es.send(entityId, any<Script>()) }
    }

    @Test
    fun `Default title Select an Option`() {
        //Given
        val suspension = OptionsDialogue(listOf("Option 1", "Option 2"), null, mockk(relaxed = true))
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { system.send(entityId, 236, 0, "Select an Option", listOf("Option 1", "Option 2")) }
    }

    @Test
    fun `Suspend process sends`() {
        //Given
        val suspension = OptionsDialogue(listOf("Option 1", "Option 2"), "Title", mockk(relaxed = true))
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { system.send(entityId, 236, 0, "Title", listOf("Option 1", "Option 2")) }
    }

    @Test
    fun `Options dialogue 6 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one", "two", "three", "four", "five", "six"), null, mockk(relaxed = true))
        }
    }

    @Test
    fun `Options dialogue 5 lines maximum`() {
        //Then
        assertDoesNotThrow {
            OptionsDialogue(listOf("one", "two", "three", "four", "five"), null, mockk(relaxed = true))
        }
    }

    @Test
    fun `Options dialogue 1 line too few`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one"), null, mockk(relaxed = true))
        }
    }

    @Test
    fun `Options dialogue 2 lines minimum`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one"), null, mockk(relaxed = true))
        }
    }

    @Test
    fun `Split test`() {
        val raw = """
                We will look after your items and money for you.
                Leave your valuables with us if you want to keep them
                safe.
            """

        val linebreaks = "One\nTwo\nthree"

        println(raw.trimIndent().lines())
        println(linebreaks.trimIndent().lines())
    }

}