package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.RegionFactory
import worlds.gregs.hestia.game.events.CreateRegion
import worlds.gregs.hestia.api.core.components.Position.Companion.regionId
import worlds.gregs.hestia.game.plugins.region.systems.load.RegionCreation
import worlds.gregs.hestia.services.getSystem

internal class RegionsSystemTest : GameTest(WorldConfigurationBuilder().with(RegionsSystem(), RegionCreation())) {

    @BeforeEach
    override fun setUp() {
        super.setUp()
        EntityFactory.add(RegionFactory())
    }

    @Test
    fun inserted() {
        val system = world.getSystem(RegionsSystem::class)
        val regionId = regionId(0, 0)
        es.dispatch(CreateRegion(regionId))
        tick()
        assertThat(system.getEntityId(regionId)).isNotNull()
    }

    @Test
    fun removed() {
        val system = world.getSystem(RegionsSystem::class)
        val regionId = regionId(0, 0)
        es.dispatch(CreateRegion(regionId))
        tick()
        var entity = system.getEntityId(regionId)
        assertThat(entity).isNotNull()
        world.delete(entity!!)
        tick()
        entity = system.getEntityId(regionId)
        assertThat(entity).isNull()
    }
}