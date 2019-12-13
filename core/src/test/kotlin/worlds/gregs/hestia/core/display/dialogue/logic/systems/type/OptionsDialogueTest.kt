package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

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
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.OptionsDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.OptionsDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.option
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.options
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.game.task.DeferQueue
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.network.client.encoders.messages.Script
import worlds.gregs.hestia.service.send

@ExtendWith(MockKExtension::class)
internal class OptionsDialogueTest : MockkGame() {

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
    fun `Deferral process ignores other types`() {
        //Given
        val deferral: DeferralType = mockk()
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.service.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { es.send(entityId, any<Script>()) }
    }

    @Test
    fun `Default title Select an Option`() {
        //Given
        val deferral = OptionsDialogue(listOf("Option 1", "Option 2"), null)
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.service.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { system.send(entityId, 236, 0, "Select an Option", listOf("Option 1", "Option 2")) }
    }

    @Test
    fun `Deferral process sends`() {
        //Given
        val deferral = OptionsDialogue(listOf("Option 1", "Option 2"), "Title")
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.service.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { system.send(entityId, 236, 0, "Title", listOf("Option 1", "Option 2")) }
    }

    @Test
    fun `Options dialogue 6 lines too many`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one", "two", "three", "four", "five", "six"), null)
        }
    }

    @Test
    fun `Options dialogue 5 lines maximum`() {
        //Then
        assertDoesNotThrow {
            OptionsDialogue(listOf("one", "two", "three", "four", "five"), null)
        }
    }

    @Test
    fun `Options dialogue 1 line too few`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one"), null)
        }
    }

    @Test
    fun `Options dialogue 2 lines minimum`() {
        //Then
        assertThrows<IllegalStateException> {
            OptionsDialogue(listOf("one"), null)
        }
    }

}