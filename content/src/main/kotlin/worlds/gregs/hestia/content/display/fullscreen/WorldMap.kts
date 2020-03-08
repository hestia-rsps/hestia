package worlds.gregs.hestia.content.display.fullscreen

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WorldMap
import worlds.gregs.hestia.core.display.interfaces.model.components.GameFrame
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.network.client.encoders.messages.WindowUpdate

IntVariable(622, Variable.Type.VARC).register("position")
IntVariable(674, Variable.Type.VARC).register("position_2")

lateinit var gameFrameMapper: ComponentMapper<GameFrame>

on<InterfaceOpened> {
    where { id == WorldMap }
    then {
        val position = entity get Position::class
        val posHash = position.x shl 14 or position.y
        entity perform SetVariable("position", posHash)
        entity perform SetVariable("position_2", posHash)
    }
}

on(InterfaceOption, "*", ids = *intArrayOf(FixedGameframe, ResizableGameframe)) { _, _, _, _ ->
    val gameFrame = gameFrameMapper.get(entity)
    entity perform CloseInterface(if (gameFrame.resizable) ResizableGameframe else FixedGameframe)
    entity perform OpenInterface(WorldMap)
}

on(InterfaceOption, "Close", id = WorldMap) { _, _, _, _ ->
    val gameFrame = gameFrameMapper.get(entity)
    entity send WindowUpdate(if (gameFrame.resizable) ResizableGameframe else FixedGameframe, 2)
}