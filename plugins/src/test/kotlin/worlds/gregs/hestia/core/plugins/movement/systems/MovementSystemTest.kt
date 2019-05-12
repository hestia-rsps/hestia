package worlds.gregs.hestia.core.plugins.movement.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.core.archetypes.RegionFactory
import worlds.gregs.hestia.core.plugins.RegionPlugin
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.dependsOn

internal class MovementSystemTest : GameTest(WorldConfigurationBuilder().dependsOn(RegionPlugin::class)) {

    /*
        walk/run/move - between regions, diagonally across regions, between chunks, between chunks and regions, local & global
     */


    @BeforeEach
    override fun setUp() {
        super.setUp()
        EntityFactory.add(RegionFactory())
    }

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