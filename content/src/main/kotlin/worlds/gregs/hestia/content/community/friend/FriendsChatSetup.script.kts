package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsChatSetup
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Script

on<WindowOpened> {
    where { target == FriendsChatSetup }
    task {
        //Send details
        GameServer.worldSession?.write(FriendsChatSettings(entity, FriendsChatSetup shl 16, 0))
    }
}

//Chat prefix
on<StringEntered> {
    whereTask { entity hasWindowOpen FriendsChatSetup }
    then { (entity, string) ->
        GameServer.worldSession?.write(FriendsChatName(entity, string))
    }
}

on<WindowInteraction> {
    where { target == FriendsChatSetup }
    task {
        val (_, _, widget, _, _, option) = event(this)
        when(widget) {
            22 -> {//Chat name
                when(option) {
                    1 -> {//Set prefix
                        entity send Script(109, "Enter chat prefix:")
                    }
                    2 -> {
                        GameServer.worldSession?.write(FriendsChatName(entity, ""))//Disable
                    }
                }
            }
            23, 24, 25, 26, 33 -> {
                GameServer.worldSession?.write(FriendsChatSettings(entity, FriendsChatSetup shl 16 or widget, option))
            }
//            3 -> {}//Close
        }
    }
}
