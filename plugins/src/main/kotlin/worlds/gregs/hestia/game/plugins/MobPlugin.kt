package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.mob.systems.MobChunkChangeSystem
import worlds.gregs.hestia.game.mob.systems.MobChunkMap
import worlds.gregs.hestia.game.plugin.Plugin.Companion.SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin

class MobPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MobChunkMap())

        //Direction
        b.with(SHIFT_PRIORITY + 1, MobChunkChangeSystem())
    }

}