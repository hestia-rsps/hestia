package worlds.gregs.hestia.core.world.land.logic.systems

import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.world.land.LandPlugin
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.artemis.dependsOn
import worlds.gregs.hestia.artemis.getSystem

internal class LandSystemTest : GameTest(WorldConfigurationBuilder().dependsOn(LandPlugin::class)) {

    /*
        TODO LandSystem needs completing first
     */

    @Test
    fun addObject() {
        val system = world.getSystem(LandSystem::class)
    }

    @Test
    fun unload() {
    }

    @Test
    fun removeObject() {
    }
}