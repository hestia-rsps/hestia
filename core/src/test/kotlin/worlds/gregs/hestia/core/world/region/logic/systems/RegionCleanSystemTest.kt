package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.Aspect
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.world.region.api.Region
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.core.entity.entity.model.components.Position.Companion.regionId
import worlds.gregs.hestia.core.world.region.model.components.DynamicRegion
import worlds.gregs.hestia.core.world.region.model.components.RegionPriorities
import worlds.gregs.hestia.core.world.region.logic.systems.load.RegionCreation

internal class RegionCleanSystemTest : GameTest(WorldConfigurationBuilder().with(RegionsSystem(), RegionCreation(), RegionCleanSystem(), RegionMock())) {

    private class UnloadException : Exception()

    private class RegionMock : Region(Aspect.all()) {
        override fun load(regionId: Int) {
        }

        override fun unload(entityId: Int) {
            throw UnloadException()
        }
    }

    @BeforeEach
    override fun setup() {
        super.setup()
        es.dispatch(CreateRegion(regionId(0, 0)))
        tick()
    }

    @Test
    fun unload() {
        val region = world.getEntity(0)
        region.edit().add(RegionPriorities())
        tick()
        assertThrows<UnloadException> {
            world.delete(0)
            tick()
        }
    }

    @Test
    fun failPriority() {
        es.dispatch(CreateRegion(regionId(1, 0)))
        tick()
        val region = world.getEntity(1)
        val priorities = RegionPriorities()
        priorities.priority = 1
        region.edit().add(priorities)
        tick()
        world.delete(0)
        tick()
    }

    @Test
    fun failDynamic() {
        val region = world.getEntity(0)
        region.edit().add(DynamicRegion()).add(RegionPriorities())
        tick()
        world.delete(0)
        tick()
    }
}