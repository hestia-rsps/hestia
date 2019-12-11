package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.services.getSystem

internal class DynamicSystemTest : GameTest(WorldConfigurationBuilder().with(DynamicSystem())) {

    @Test
    fun isDynamic() {
        val system = world.getSystem(DynamicSystem::class)
        assertThat(system.isDynamic(0)).isFalse()
    }

    @Test
    fun create() {
        val system = world.getSystem(DynamicSystem::class)
        system.create(0)
        assertThat(system.isDynamic(0)).isTrue()
    }

    @Test
    fun get() {
        val system = world.getSystem(DynamicSystem::class)
        assertThat(system.get(0)).isNull()
        system.create(0)
        assertThat(system.get(0)).isNotNull
    }
}