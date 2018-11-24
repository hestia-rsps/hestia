package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.systems.PacketSender

class NetworkPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PacketSender())
    }

}