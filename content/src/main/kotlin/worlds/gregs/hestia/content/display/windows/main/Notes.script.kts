package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Notes
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

on<WindowOpened> {
    where { target == Notes }
    then {
        entity send WidgetSettings(Notes, 9, 0, 30, 2621470)
    }
}