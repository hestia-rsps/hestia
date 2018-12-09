package worlds.gregs.hestia.game.plugins

import com.artemis.MundaneWireException
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.game.GameTest

internal class PluginTest : GameTest(WorldConfigurationBuilder()) {

    @Test
    fun testCore() {
        build(WorldConfigurationBuilder())
    }

    @Test
    fun testClient() {
        build(WorldConfigurationBuilder().with(ClientPlugin()), false)
        build(WorldConfigurationBuilder().with(EntityPlugin()), false)
        build(WorldConfigurationBuilder().with(CorePlugin()), false)
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
    fun testMob() {
        assertThrows<MundaneWireException> {
            build(WorldConfigurationBuilder().with(MobPlugin()))
        }
        build(WorldConfigurationBuilder().with(MobPlugin(), EntityPlugin()))
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
            builder.with(CorePlugin(), ClientPlugin())
        }
        World(builder.build())
    }
}