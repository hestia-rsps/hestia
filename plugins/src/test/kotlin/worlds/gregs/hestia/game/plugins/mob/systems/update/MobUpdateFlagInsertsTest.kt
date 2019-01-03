package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.MobUpdateFlagSystem
import worlds.gregs.hestia.services.getSystem

internal class MobUpdateFlagInsertsTest : GameTest(WorldConfigurationBuilder().with(MobUpdateFlagSystem(), MobUpdateFlagInserts())) {

    @Test
    fun initialise() {
        val system = world.getSystem(MobUpdateFlagSystem::class)
        val flags = system.updateFlags
        val correctFlags = listOf(0x100000, 0x1, 0x20000, 0x40, 0x100, 0x40000, 0x20, 0x2, 0x8, 0x80000, 0x400, 0x10, 0x800, 0x4000, 0x1000, 0x4, 0x200)
        flags.forEachIndexed { index, updateFlag ->
            Assertions.assertThat(updateFlag.mask).isEqualTo(correctFlags[index])
        }
    }
}