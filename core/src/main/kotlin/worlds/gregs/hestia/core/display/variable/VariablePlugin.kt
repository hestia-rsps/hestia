package worlds.gregs.hestia.core.display.variable

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.request.logic.RequestSystem
import worlds.gregs.hestia.core.display.variable.logic.VariableSystem
import worlds.gregs.hestia.game.plugin.Plugin

class VariablePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(VariableSystem())
    }

}