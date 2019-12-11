package worlds.gregs.hestia.game.plugin

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import worlds.gregs.hestia.artemis.event.ExtendedFastEventDispatcher

internal class PluginBaseTest {

    lateinit var base: PluginBase

    @BeforeEach
    fun setup() {
        base = object : PluginBase() {
        }
    }

    @Test
    fun `Setup world`() {
        //Given
        val mock = mock<WorldConfigurationBuilder.() -> Unit>()
        val builder = mock<WorldConfigurationBuilder>()
        base.setup(mock)
        //When
        base.setup(builder)
        //Then
        verify(mock).invoke(eq(builder))
    }
    
    @Test
    fun `Initiate world`() {
        //Given
        val mock = mock<(World, ExtendedFastEventDispatcher) -> Unit>()
        val world = mock<World>()
        val dispatcher = mock<ExtendedFastEventDispatcher>()
        base.init(mock)
        //When
        base.init(world, dispatcher)
        //Then
        verify(mock).invoke(eq(world), eq(dispatcher))
    }

    @Test
    fun `No setup doesn't crash`() {
        //Then
        assertDoesNotThrow {
            base.setup(mock<WorldConfigurationBuilder>())
            base.init(mock(), mock())
        }
    }
}