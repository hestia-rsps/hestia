package worlds.gregs.hestia.core.action

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.action.logic.systems.*
import worlds.gregs.hestia.game.plugin.Plugin

class ActionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ActionSystem(), InterfaceOptionSystem())
        b.with(FloorItemOptionSystem(), ObjectOptionSystem(), NpcOptionSystem(), PlayerOptionSystem())
    }

}