package worlds.gregs.hestia.game.plugins.core.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.services.getSystem

internal class TickTaskSystemTest : GameTest(WorldConfigurationBuilder().with(TickTaskSystem())) {

    @Test
    fun scheduleInstant() {
        val system = world.getSystem(TickTaskSystem::class)
        var counter = 0
        system.schedule(0, 0) {
            counter++
        }
        assertThat(system.tasksCount).isEqualTo(1)
        tick()
        assertThat(system.tasksCount).isEqualTo(0)
        tick()
        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun scheduleDelay() {
        val system = world.getSystem(TickTaskSystem::class)
        var counter = 0
        system.schedule(1, 0) {
            counter++
        }
        assertThat(system.tasksCount).isEqualTo(1)
        tick()
        assertThat(system.tasksCount).isEqualTo(1)
        tick()
        assertThat(system.tasksCount).isEqualTo(0)
        tick()
        assertThat(counter).isEqualTo(1)
    }

    @Test
    fun schedulePeriod() {
        val system = world.getSystem(TickTaskSystem::class)
        var counter = 0
        system.schedule(0, 1) {
            counter++
        }
        tick()
        assertThat(system.tasksCount).isEqualTo(1)
        tick()
        assertThat(system.tasksCount).isEqualTo(1)
        tick()
        assertThat(counter).isEqualTo(3)
    }
}