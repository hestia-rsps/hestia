package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.artemis.dependsOn
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.region.RegionPlugin

internal class MovementSystemTest : GameTest(WorldConfigurationBuilder().dependsOn(RegionPlugin::class)) {

    /*
        walk/run/move - between regions, diagonally across regions, between chunks, between chunks and regions, local & global
     */

    @Test
    fun walk() {
        val entity = create()
        entity
    }

    private fun create(): Entity {
        val entity = world.createEntity()
        entity.edit().add(Position()).add(Mobile())
        tick()
        return entity
    }
}