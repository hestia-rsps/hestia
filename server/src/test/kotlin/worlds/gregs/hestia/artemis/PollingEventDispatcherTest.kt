package worlds.gregs.hestia.artemis

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.event.common.SubscribeAnnotationFinder
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTimeout
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.event.PollingEventDispatcher
import java.time.Duration
import java.util.*

internal class PollingEventDispatcherTest  {

    private class TestEvent(val value: Int = 0) : Event

    private class TestListener : PassiveSystem() {

        val collected = LinkedList<Event>()
        private lateinit var eventSystem: EventSystem

        @Subscribe
        fun event(event: TestEvent) {
            collected.add(event)
            eventSystem.dispatch(event)
        }
    }

    @Test
    fun `Infinite loop test`() {
        //Given
        val system = TestListener()
        val es = EventSystem(PollingEventDispatcher(), SubscribeAnnotationFinder())
        val world = World(WorldConfigurationBuilder().with(system, es).build())
        //When
        es.dispatch(TestEvent())
        assertTimeout(Duration.ofMillis(1000)) {
            world.process()
        }
        //Then
        assertThat(system.collected.size).isEqualTo(1)
    }

    @Test
    fun `Receives all events`() {
        //Given
        val system = TestListener()
        val es = EventSystem(PollingEventDispatcher(), SubscribeAnnotationFinder())
        val world = World(WorldConfigurationBuilder().with(system, es).build())
        //When
        repeat(100) {
            es.dispatch(TestEvent())
        }
        world.process()
        //Then
        assertThat(system.collected.size).isEqualTo(100)
    }

    @Test
    fun `Maintains order`() {
        //Given
        val system = TestListener()
        val es = EventSystem(PollingEventDispatcher(), SubscribeAnnotationFinder())
        val world = World(WorldConfigurationBuilder().with(system, es).build())
        //When
        repeat(100) {
            es.dispatch(TestEvent(it))
        }
        world.process()
        //Then
        system.collected.filterIsInstance<TestEvent>().forEachIndexed { index, testEvent ->
            assertThat(testEvent.value).isEqualTo(index)
        }
        assertThat(system.collected.size).isEqualTo(100)
    }

    @Test
    fun `No events received early`() {
        //Given
        val system = TestListener()
        val es = EventSystem(PollingEventDispatcher(), SubscribeAnnotationFinder())
        World(WorldConfigurationBuilder().with(system, es).build())
        //When
        repeat(100) {
            es.dispatch(TestEvent())
        }
        //Then
        assertThat(system.collected.size).isEqualTo(0)
    }

}