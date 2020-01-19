package worlds.gregs.hestia.core.plugins

import com.artemis.MundaneWireException
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.display.client.ClientPlugin
import worlds.gregs.hestia.core.entity.entity.EntityPlugin
import worlds.gregs.hestia.core.entity.npc.NpcPlugin
import worlds.gregs.hestia.core.entity.player.PlayerPlugin
import worlds.gregs.hestia.core.world.land.LandPlugin
import worlds.gregs.hestia.core.world.map.MapPlugin
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.region.RegionPlugin

internal class PluginTest : GameTest(WorldConfigurationBuilder()) {

    @Test
    fun testCore() {
        build(WorldConfigurationBuilder())
    }

    @Test
    fun testClient() {
        build(WorldConfigurationBuilder().with(ClientPlugin()), false)
        build(WorldConfigurationBuilder().with(EntityPlugin()), false)
    }


    @Test
    fun testRegion() {
        //Individually
        build(WorldConfigurationBuilder().with(RegionPlugin()))
        build(WorldConfigurationBuilder().with(MapPlugin()))
        build(WorldConfigurationBuilder().with(LandPlugin()))
        //Together
        build(WorldConfigurationBuilder().with(RegionPlugin(), MapPlugin(), LandPlugin()))
    }

    @Test
    fun testPlayer() {
        assertThrows<MundaneWireException> {
            build(WorldConfigurationBuilder().with(PlayerPlugin()))
        }
        build(WorldConfigurationBuilder().with(PlayerPlugin(), EntityPlugin()))
    }

    @Test
    fun testNpc() {
        assertThrows<MundaneWireException> {
            build(WorldConfigurationBuilder().with(NpcPlugin()))
        }
        build(WorldConfigurationBuilder().with(NpcPlugin(), EntityPlugin()))
    }

    @Test
    fun testMovement() {
        build(WorldConfigurationBuilder().with(MovementPlugin()))
    }

    @Throws(MundaneWireException::class)
    private fun build(builder: WorldConfigurationBuilder, core: Boolean = true) {
        builder.with(EventSystem())
        //Core plugins
        if(core) {
            builder.with(ClientPlugin())
        }
        World(builder.build())
    }
}