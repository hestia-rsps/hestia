package worlds.gregs.hestia.core.script

import com.artemis.*
import com.nhaarman.mockitokotlin2.*
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Matchers.anyInt
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.event.ExtendedEventDispatchStrategy
import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.action.model.WorldAction

internal class ScriptBaseTest {

    lateinit var script: ScriptBase

    @BeforeEach
    fun setup() {
        script = object : ScriptBase() {}
    }

    @Test
    fun `Add event listener`() {
        //When
        script.on<Action>(action = {})
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
    fun `Registers listeners`() {
        //Given
        script.on<Action>(action = {})
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

}