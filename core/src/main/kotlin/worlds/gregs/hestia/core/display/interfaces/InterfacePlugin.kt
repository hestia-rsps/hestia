package worlds.gregs.hestia.core.display.interfaces

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.interfaces.logic.systems.ContextMenuSystem
import worlds.gregs.hestia.core.display.variable.logic.VariableSystem
import worlds.gregs.hestia.core.display.interfaces.logic.systems.InterfaceSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.INTERFACE_PRIORITY

class InterfacePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ContextMenuSystem())
        b.with(INTERFACE_PRIORITY, InterfaceSystem())
    }

}