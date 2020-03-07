package worlds.gregs.hestia.content.display.fullscreen

import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WorldMap
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.script.on

IntVariable(622, Variable.Type.VARC).register("position")
IntVariable(674, Variable.Type.VARC).register("position_2")

on<InterfaceOpened> {
    where { id == WorldMap }
    then {
        val position = entity get Position::class
        val posHash = position.x shl 14 or position.y
        entity perform SetVariable("position", posHash)//FIXME
        entity perform SetVariable("position_2", posHash)
    }
}


on(InterfaceOption, "*", ids = *intArrayOf(FixedGameframe, ResizableGameframe)) { _, _, _, _ ->
    entity perform OpenInterface(WorldMap, true)
}

on(InterfaceOption, "Close", id = WorldMap) { _, _, _, _ ->
}