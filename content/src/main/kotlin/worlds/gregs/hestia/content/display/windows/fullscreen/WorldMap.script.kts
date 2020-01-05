package worlds.gregs.hestia.content.display.windows.fullscreen

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WorldMap
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.network.client.encoders.messages.ConfigGlobal

lateinit var es: EventSystem
lateinit var positionMapper: ComponentMapper<Position>

on<WindowOpened> {
    where { target == WorldMap }
    then { (entityId, _) ->
        val position = positionMapper.get(entityId)
        val posHash = position.x shl 14 or position.y
        es.send(entityId, ConfigGlobal(622, posHash))
        es.send(entityId, ConfigGlobal(674, posHash))
    }
}

on<WindowInteraction> {
    where { target == WorldMap }
    then { (_, _, widget) ->
        if(widget == 44) {
            //Close button
        }
    }
}