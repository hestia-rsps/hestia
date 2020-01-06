package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Emotes
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

on<WindowOpened> {
    where { target == Emotes }
    then {
        for (widget in 11..14) {
            entity send WidgetSettings(Emotes, widget, -1, 190, 2150)
        }

        entity send Config(Configs.GOBLIN_QUEST_EMOTES, 7)
        entity send Config(Configs.STRONGHOLD_SECURITY_EMOTES, 7)
        entity send Config(Configs.HALLOWEEN_EMOTES, 249852)
        entity send Config(Configs.EVENT_EMOTES, 65535)
    }
}