package worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag

import com.artemis.Aspect
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import worlds.gregs.hestia.api.client.PlayerUpdateFlags
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.PlayerUpdateFlagSystem
import worlds.gregs.hestia.game.update.UpdateEncoder
import worlds.gregs.hestia.services.getSystem

internal class BaseUpdateFlagSystemTest : GameTest(WorldConfigurationBuilder().with(PlayerUpdateFlagSystem())) {

    private lateinit var system: PlayerUpdateFlags
    private val emptyEncoder = object : UpdateEncoder {
        override val encode: PacketBuilder.(Int, Int) -> Unit
            get() = { _, _ -> }
    }

    @BeforeEach
    fun before() {
        system = world.getSystem(PlayerUpdateFlagSystem::class)
        system.updateFlags.clear()
    }

    @Test
    fun insertAfter() {
        system.insert(system.create(0x1, Aspect.all(), emptyEncoder))
        assertThat(system.updateFlags.size).isEqualTo(1)

        //Failed insert
        assertThrows<IndexOutOfBoundsException> {
            system.insertAfter(0x4, system.create(0x2, Aspect.all(), emptyEncoder))
        }
        assertThat(system.updateFlags.size).isEqualTo(1)
        system.insert(system.create(0x2, Aspect.all(), emptyEncoder))
        assertThat(system.updateFlags.size).isEqualTo(2)

        //Successful insert
        val flag = system.create(0x4, Aspect.all(), emptyEncoder)
        system.insertAfter(0x1, flag)
        assertThat(system.updateFlags.size).isEqualTo(3)
        assertThat(system.updateFlags[1]).isEqualTo(flag)
    }

    @Test
    fun insert() {
        //Insert single
        system.insert(system.create(0x1, Aspect.all(), emptyEncoder))
        assertThat(system.updateFlags.size).isEqualTo(1)
        system.insert(system.create(0x2, Aspect.all(), emptyEncoder))
        assertThat(system.updateFlags.size).isEqualTo(2)

        //Insert multiple
        system.insert(system.create(0x3, Aspect.all(), emptyEncoder), system.create(0x4, Aspect.all(), emptyEncoder))
        assertThat(system.updateFlags.size).isEqualTo(4)
        assertThat(system.updateFlags[2].mask).isEqualTo(0x3)
    }

    @Test
    fun create() {
        val mask = 0x8000
        val aspect = Aspect.all()
        val added = true
        val flag = system.create(mask, aspect, emptyEncoder, added)

        assertThat(flag.mask).isEqualTo(mask)
        assertThat(flag.subscription.aspect).isEqualToComparingFieldByField(aspect.build(world))
        assertThat(flag.unit).isEqualTo(emptyEncoder)
        assertThat(flag.added).isEqualTo(added)
    }
}