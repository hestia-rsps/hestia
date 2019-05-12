package worlds.gregs.hestia.core.scripts

import com.artemis.*
import com.nhaarman.mockitokotlin2.*
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import org.mockito.Matchers.anyInt
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.artemis.event.ExtendedEventDispatchStrategy
import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueQueue
import worlds.gregs.hestia.game.queue.QueueScope
import worlds.gregs.hestia.services.Aspect

internal class ScriptBaseTest {

    lateinit var script: ScriptBase

    @BeforeEach
    fun setup() {
        script = object : ScriptBase() {}
    }

    @Test
    fun `Add event listener`() {
        //When
        script.on<Event>(action = {})
        //Then
        assertThat(script.listeners).isNotEmpty
    }

    @Test
    fun `Subscribe to entity events`() {
        //When
        script.subscribe(Aspect.all(Component::class))
        //Then
        assertThat(script.subscriptions).isNotEmpty
    }

    @Test
    fun `Can't subscribe to empty aspect`() {
        //Then
        assertThrows<IllegalArgumentException> {
            script.subscribe()
        }
    }

    @Test
    fun `Create a system`() {
        //When
        script.system {  }
        //Then
        assertThat(script.systems).isNotEmpty
    }

    @Test
    fun `Builds systems`() {
        //Given
        script.system { priority = 4 }
        //When
        val config = mock<WorldConfigurationBuilder>()
        script.build(config)
        //Then
        verify(config, times(1)).with(eq(4), any<PassiveSystem>())
    }

    @Test
    fun `Doesn't build null systems`() {
        //Given
        script.system { aspect all Component::class }
        //When
        val config = mock<WorldConfigurationBuilder>()
        script.build(config)
        //Then
        verify(config, times(0)).with(anyInt(), any())
    }

    @Test
    fun `Add named dialogue`() {
        //Given
        val name = "name"
        val action = mock<DialogueQueue>()
        //When
        script.dialogue(name, action)
        //Then
        assertThat(script.dialogues).containsEntry(name, action)
    }

    @Test
    fun `Doesn't add unnamed dialogue`() {
        //Given
        val action = mock<DialogueQueue>()
        //When
        script.dialogue(action)
        //Then
        assertThat(script.dialogues).isEmpty()
    }

    @Test
    fun `Registers listeners`() {
        //Given
        script.on<Event>(action = {})
        val world = mock<World>()
        whenever(world.aspectSubscriptionManager).thenReturn(mock())
        val dispatcher = mock<ExtendedEventDispatchStrategy>()
        //When
        script.build(world, dispatcher)
        //Then
        assertThat(script.listeners).isNotEmpty
        verify(dispatcher, times(1)).register(any())
    }

    @Test
    fun `Registers subscriptions`() {
        //Given
        script.subscribe(Aspect.all(Component::class))
        val world = mock<World>()
        val manager = mock<AspectSubscriptionManager>()
        val aspect = mock<EntitySubscription>()
        val dispatcher = mock<ExtendedEventDispatchStrategy>()
        whenever(world.aspectSubscriptionManager).thenReturn(manager)
        whenever(manager.get(any())).thenReturn(aspect)
        //When
        script.build(world, dispatcher)
        //Then
        assertThat(script.subscriptions).isNotEmpty
        verify(aspect, times(1)).addSubscriptionListener(any())
    }

    @Test
    fun `Registers dialogues`() {
        //Given
        val dialogueSystem = mock<DialogueBase>()
        val dialogue = mock<DialogueQueue>()
        val name = "name"
        script.dialogues[name] = dialogue
        script.dialogueBase = dialogueSystem

        val world = mock<World>()
        whenever(world.aspectSubscriptionManager).thenReturn(mock())
        val dispatcher = mock<ExtendedEventDispatchStrategy>()
        //When
        script.build(world, dispatcher)
        //Then
        verify(dialogueSystem).addDialogue(eq(name), eq(dialogue))
    }

    @Test
    fun `Queue returns self`() {
        //Given
        val action: suspend QueueScope<Nothing>.() -> Unit = {
            fail("Action shouldn't have been called.")
        }
        //When
        val queue = script.queue(action)
        //Then
        assertEquals(action, queue)
    }

}