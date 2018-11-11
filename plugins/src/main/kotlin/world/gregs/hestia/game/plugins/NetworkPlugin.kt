package world.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import world.gregs.hestia.game.plugin.Plugin
import world.gregs.hestia.game.systems.PacketSender

class NetworkPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(PacketSender())
    }

}