package worlds.gregs.hestia.core.display.request

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.request.logic.RequestSystem
import worlds.gregs.hestia.game.plugin.Plugin

class RequestPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(RequestSystem())
    }

}