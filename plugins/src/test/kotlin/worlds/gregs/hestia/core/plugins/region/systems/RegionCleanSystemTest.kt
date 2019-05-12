package worlds.gregs.hestia.core.plugins.region.systems

import com.artemis.Aspect
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.api.region.Region
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.core.archetypes.RegionFactory
import worlds.gregs.hestia.artemis.events.CreateRegion
import worlds.gregs.hestia.game.entity.components.Position.Companion.regionId
import worlds.gregs.hestia.api.region.components.DynamicRegion
import worlds.gregs.hestia.core.plugins.region.components.RegionPriorities
import worlds.gregs.hestia.core.plugins.region.systems.load.RegionCreation

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
    override fun setUp() {
        super.setUp()
        EntityFactory.add(RegionFactory())
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