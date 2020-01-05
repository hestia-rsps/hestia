package worlds.gregs.hestia.core.display.window

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.window.logic.systems.ContextMenuSystem
import worlds.gregs.hestia.core.display.window.logic.systems.RequestSystem
import worlds.gregs.hestia.core.display.window.logic.systems.WindowSystem
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.INTERFACE_PRIORITY

class WindowPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ContextMenuSystem(), RequestSystem())
        b.with(INTERFACE_PRIORITY, WindowSystem())
    }

}