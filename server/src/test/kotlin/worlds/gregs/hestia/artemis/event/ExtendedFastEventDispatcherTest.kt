package worlds.gregs.hestia.artemis.event

import net.mostlyoriginal.api.event.common.Event
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.dsl.ArtemisEventListener

internal class ExtendedFastEventDispatcherTest {

    @Test
    fun `Registered listener dispatches`() {
        //Given
        val dispatcher = object : ExtendedFastEventDispatcher() {}
        val event = object : Event {}
        var counter = 0
        val eventListener = ArtemisEventListener(event::class, 0, false, null) {
            counter++
        }
        dispatcher.register(eventListener)
        //When
        dispatcher.dispatch(event)
        //Then
        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun `Registered listener dispatches in correct order`() {
        //Given
        val dispatcher = object : ExtendedFastEventDispatcher() {}
        val event = object : Event {}
        var counter = 0
        //When
        //Registered first, but low priority called last
        dispatcher.register(ArtemisEventListener(event::class, 2, false, null) {
            assertThat(counter++).isEqualTo(1)
        })
        //Registered last, but priority called first
        dispatcher.register(ArtemisEventListener(event::class, 10, false, null) {
            counter++
        })
        dispatcher.dispatch(event)
        //Then
        assertThat(counter).isEqualTo(2)
    }
}