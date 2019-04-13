package worlds.gregs.hestia.api.client

import com.artemis.WorldConfigurationBuilder
import com.artemis.WorldConfigurationBuilder.Priority.HIGHEST
import worlds.gregs.hestia.api.client.update.ClientUpdatePlugin
import worlds.gregs.hestia.game.client.DisconnectSystem
import worlds.gregs.hestia.game.plugin.Plugin

class ClientPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(HIGHEST - 2, DisconnectSystem())
        b.dependsOn(ClientUpdatePlugin::class.java)
    }

}