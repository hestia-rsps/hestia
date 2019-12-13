package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.core.world.region.logic.RegionFactory
import worlds.gregs.hestia.core.world.region.model.components.RegionPriorities
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionCreation
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.entity.entity.logic.EntityFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position.Companion.regionId
import worlds.gregs.hestia.core.getComponent
import worlds.gregs.hestia.service.getSystem

internal class RegionPrioritySystemTest : GameTest(WorldConfigurationBuilder().with(RegionPrioritySystem(), RegionCreation(), RegionsSystem())) {


    @BeforeEach
    override fun setup() {
        super.setup()
        EntityFactory.add(RegionFactory())
        es.dispatch(CreateRegion(regionId(0, 0)))
        tick()
    }

    @Test
    fun add() {
        val system = world.getSystem(RegionPrioritySystem::class)
        system.add(regionId(0, 0))
        val region = world.getEntity(0)
        assertThat(region.getComponent(RegionPriorities::class)?.priority ?: -1).isEqualTo(1)
    }

    @Test
    fun remove() {
        val system = world.getSystem(RegionPrioritySystem::class)
        val regionId = regionId(0, 0)
        system.add(regionId)
        tick()
        val region = world.getEntity(0)
        val priorities = region.getComponent(RegionPriorities::class)!!
        priorities.priority = 2
        //Deduction
        system.remove(regionId)
        assertThat(priorities.priority).isEqualTo(1)
        //Removal
        system.remove(regionId)
        assertThat( region.getComponent(RegionPriorities::class)).isNull()
    }
}