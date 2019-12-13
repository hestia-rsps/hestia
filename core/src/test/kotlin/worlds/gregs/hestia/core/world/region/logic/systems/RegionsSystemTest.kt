package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.core.world.region.logic.RegionFactory
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionCreation
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.entity.entity.logic.EntityFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position.Companion.regionId
import worlds.gregs.hestia.service.getSystem

internal class RegionsSystemTest : GameTest(WorldConfigurationBuilder().with(RegionsSystem(), RegionCreation())) {

    @BeforeEach
    override fun setup() {
        super.setup()
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