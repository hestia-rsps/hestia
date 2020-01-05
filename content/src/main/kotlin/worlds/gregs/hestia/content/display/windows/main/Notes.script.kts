package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Notes
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.WindowWidgetSettings

lateinit var es: EventSystem

on<WindowOpened> {
    where { target == Notes }
    then { (entityId, _) ->
        es.send(entityId, WindowWidgetSettings(Notes, 9, 0, 30, 2621470))
    }
}