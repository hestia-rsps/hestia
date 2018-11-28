package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.PlayerUpdateFlagSystem
import worlds.gregs.hestia.services.getSystem

internal class PlayerUpdateFlagInsertsTest : GameTest(WorldConfigurationBuilder().with(PlayerUpdateFlagSystem(), PlayerUpdateFlagInserts())) {

    @Test
    fun initialise() {
        val system = world.getSystem(PlayerUpdateFlagSystem::class)
        val flags = system.updateFlags
        val correctFlags = listOf(0x8000, 0x40, 0x40000, 0x20000, 0x200, 0x2000, 0x80000, 0x100000, 0x4, 0x10000, 0x8, 0x4000, 0x400, 0x1, 0x10, 0x1000, 0x20, 0x2, 0x100)
        flags.forEachIndexed { index, updateFlag ->
            assertThat(updateFlag.mask).isEqualTo(correctFlags[index])
        }
    }
}