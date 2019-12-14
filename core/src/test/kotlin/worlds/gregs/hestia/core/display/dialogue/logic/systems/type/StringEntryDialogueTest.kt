package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StringEntryDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StringEntryDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.stringEntry
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.network.client.encoders.messages.Script

@ExtendWith(MockKExtension::class)
internal class StringEntryDialogueTest : MockkGame() {

    @SpyK
    var system = StringEntryDialogueSystem()

    @RelaxedMockK
    private lateinit var scope: TaskScope

    @RelaxedMockK
    private lateinit var queue: Tasks

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, queue, DialogueBoxSystem())
    }

    @Test
    fun `String entry sets dialogue, suspends and returns value`() = runBlocking {
        //Given
        every { scope.deferral = any() } propertyType StringEntryDialogue::class answers {
            value.entry = "A string"
        }
        //When
        val result = scope.stringEntry("Title")
        //Then
        assertEquals("A string", result)
        coVerifySequence {
            scope.deferral = StringEntryDialogue("Title")
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Null pointer if value not set`() {
        //Then
        assertThrows<KotlinNullPointerException> {
            runBlocking {
                scope.stringEntry("Title")
            }
        }
    }

    @Test
    fun `Deferral process ignores other types`() {
        //Given
        val deferral: DeferralType = mockk()
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { es.send(entityId, any<Script>()) }
    }

    @Test
    fun `Deferral process sends script 108`() {
        //Given
        val deferral = StringEntryDialogue("Title")
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { es.send(entityId, Script(108, "Title")) }
    }

    @Test
    fun `String entered ignored if wrong type`() {
        //Given
        val entityId = 0
        val deferral: Dialogue = mockk()
        every { system.getDeferral(entityId) } returns deferral
        //When
        es.dispatch(StringEntered(entityId, "text"))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }

    @Test
    fun `String entered`() {
        //Given
        val entityId = 0
        val deferral = spyk(StringEntryDialogue("Title"))
        every { system.getDeferral(entityId) } returns deferral
        //When
        es.dispatch(StringEntered(entityId, "text"))
        //Then
        verifyOrder {
            deferral.entry = "text"
            queue.resume(entityId)
        }
    }
}