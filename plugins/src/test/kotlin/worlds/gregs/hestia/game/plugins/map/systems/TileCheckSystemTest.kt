package worlds.gregs.hestia.game.plugins.map.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.RegionFactory
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.plugins.LandPlugin
import worlds.gregs.hestia.game.plugins.MapPlugin
import worlds.gregs.hestia.game.plugins.RegionPlugin
import worlds.gregs.hestia.game.plugins.core.systems.cache.CacheSystem
import worlds.gregs.hestia.game.plugins.region.systems.RegionSystem
import worlds.gregs.hestia.game.plugins.region.systems.RegionsSystem
import worlds.gregs.hestia.services.dependsOn
import worlds.gregs.hestia.services.getSystem

internal class TileCheckSystemTest : GameTest(WorldConfigurationBuilder().dependsOn(MapPlugin::class, LandPlugin::class, RegionPlugin::class).with(CacheSystem())) {

    @BeforeEach
    override fun setUp() {
        super.setUp()
        EntityFactory.add(RegionFactory())
    }

    @Test
    fun checkWalkStep() {
        //Load region
        val region = world.getSystem(RegionSystem::class)
        region.load(12342)
        tick()
        //Set a un-walkable tile
        val entityId = world.getSystem(RegionsSystem::class).getEntityId(12342)!!
        val clip = world.getSystem(ClippingMaskSystem::class)
        clip.addMask(entityId, 23, 35, 0, Flags.FLOOR_BLOCKS_WALK)

        //Check tile clipping
        val system = world.getSystem(TileCheckSystem::class)
        //Can walk off of a blocked tile
        assertThat(system.isTileWalkable(3095, 3491, 0, 4, 1,1)).isTrue()
        //Can't walk onto a blocked tile
        assertThat(system.isTileWalkable(3094, 3491, 0, 4, 1,1)).isFalse()
        //No matter how wide you are
//        assertThat(system.isTileWalkable(3094, 3491, 0, 4, 2,1)).isFalse() TODO fix
        //Or angle you approach
        assertThat(system.isTileWalkable(3094, 3490, 0, 2, 1,1)).isFalse()
    }
}