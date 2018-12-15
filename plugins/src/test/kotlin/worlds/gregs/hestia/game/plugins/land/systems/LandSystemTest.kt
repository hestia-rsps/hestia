package worlds.gregs.hestia.game.plugins.land.systems

import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.plugins.LandPlugin
import worlds.gregs.hestia.services.dependsOn
import worlds.gregs.hestia.services.getSystem

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