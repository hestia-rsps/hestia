package worlds.gregs.hestia.core.scripts.dsl

import net.mostlyoriginal.api.event.common.Event
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class EventListenerBuilderTest {

    @Test
    fun `'where' sets conditional`() {
        //Given
        val builder = EventListenerBuilder(Event::class, 0, false, null, action = {})
        //When
        val function: (Event) -> Boolean = { true }
        builder.where(function)
        //Then
        assertThat(builder.build()!!.conditional.hashCode()).isEqualTo(function.hashCode())//Not directly equal due to generics
    }

    @Test
    fun `Can't override conditional`() {
        //Given
        val builder = EventListenerBuilder(Event::class, 0, false, null, null)
        //When
        builder.where { true }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.where { true }
        }
    }

    @Test
    fun `'then' sets action`() {
        //Given
        val builder = EventListenerBuilder(Event::class, 0, false, null, null)
        //When
        val function: (Event) -> Unit = { }
        builder.then(function)
        //Then
        assertThat(builder.build()!!.action).isEqualTo(function)
    }

    @Test
    fun `Can't override action`() {
        //Given
        val builder = EventListenerBuilder(Event::class, 0, false, null, null)
        //When
        builder.then {  }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.then {  }
        }
    }

    @Test
    fun `Null build without action`() {
        //Given
        val builder = EventListenerBuilder(Event::class, 0, false, null, null)
        //Then
        assertThat(builder.build()).isNull()
    }

}