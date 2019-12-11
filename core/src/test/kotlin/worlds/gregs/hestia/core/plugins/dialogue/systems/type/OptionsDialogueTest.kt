package worlds.gregs.hestia.core.plugins.dialogue.systems.type

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.api.task.Tasks
import worlds.gregs.hestia.api.task.events.ProcessDeferral
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.OptionsDialogue
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.OptionsDialogueSystem
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.option
import worlds.gregs.hestia.core.plugins.dialogue.systems.types.options
import worlds.gregs.hestia.core.plugins.widget.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.game.MockkGameTest
import worlds.gregs.hestia.game.task.DeferQueue
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.network.client.encoders.messages.Script
import worlds.gregs.hestia.services.send

@ExtendWith(MockKExtension::class)
internal class OptionsDialogueTest : MockkGameTest() {

    @SpyK
    var system = OptionsDialogueSystem()

    @RelaxedMockK
    private lateinit var scope: TaskScope

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, mockk<Tasks>(relaxed = true), DialogueBoxSystem())
    }

    @Test
    fun `Option dialogue sets and suspends`() = runBlocking {
        //Given
        every { scope.deferral = any() } propertyType OptionsDialogue::class answers {
            value.selected = 2
        }
        //When
        val response = scope.option("Title", "Option 1", "Option 2")
        //Then
        assertEquals(2, response)
        coVerifySequence {
            scope.deferral = OptionsDialogue(listOf("Option 1", "Option 2"), "Title")
            scope.defer()
        }
        confirmVerified(scope)
    }

    @Test
    fun `Options dialogue sets data, suspends and fires action`() = runBlocking {
        //Given
        every { scope.deferral = any() } propertyType OptionsDialogue::class answers {
            value.selected = 2
        }
        val firstAction: DeferQueue = mockk()
        val secondAction: DeferQueue = mockk(relaxed = true)
        //When
        scope.options("Option 1", firstAction, "Option 2", secondAction)
        //Then
        coVerifySequence {
            scope.deferral = OptionsDialogue(listOf("Option 1", "Option 2"), null)
            scope.defer()
            secondAction.invoke(allAny())
        }
        confirmVerified(scope)
        confirmVerified(secondAction)
    }

    @Test
    fun `Null pointer if value not set`() {
        //Then
        assertThrows<KotlinNullPointerException> {
            runBlocking {
                scope.option("Title")
            }
        }
    }

    @Test
    fun `Deferral process ignores other types`() {
        //Given
        val deferral: DeferralType = mockk()
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { es.send(entityId, any<Script>()) }
    }

    @Test
    fun `Default title Select an Option`() {
        //Given
        val deferral = OptionsDialogue(listOf("Line"), null)
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { es.send(entityId, Script(108, "Select an Option")) }
    }

    @Test
    fun `Deferral process sends script 108`() {
        //Given
        val deferral = OptionsDialogue(listOf("Line"), "Title")
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { es.send(entityId, Script(108, "Title")) }
    }

    @Test
    fun `Options dialogue 6 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one", "two", "three", "four", "five", "six"), null)
        }
    }

    @Test
    fun `Options dialogue 5 lines fine`() {
        //Then
        assertDoesNotThrow {
            OptionsDialogue(listOf("one", "two", "three", "four", "five"), null)
        }
    }

}