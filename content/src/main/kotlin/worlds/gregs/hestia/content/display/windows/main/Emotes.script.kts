package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Emotes
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings

lateinit var es: EventSystem

on<WindowOpened> {
    where { target == Emotes }
    then { (entityId, _) ->
        for (componentId in 11..14) {
            es.send(entityId, WidgetComponentSettings(Emotes, componentId, -1, 190, 2150))
        }

        es.send(entityId, Config(Configs.GOBLIN_QUEST_EMOTES, 7))
        es.send(entityId, Config(Configs.STRONGHOLD_SECURITY_EMOTES, 7))
        es.send(entityId, Config(Configs.HALLOWEEN_EMOTES, 249852))
        es.send(entityId, Config(Configs.EVENT_EMOTES, 65535))
    }
}