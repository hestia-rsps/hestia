package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsChatSetup
import worlds.gregs.hestia.core.display.window.logic.systems.WindowSystem
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Script

on<WindowOpened> {
    where { target == FriendsChatSetup }
    then {
        //Send details
        GameServer.worldSession?.write(FriendsChatSettings(entity, FriendsChatSetup shl 16, 0))
    }
}

//Chat prefix
on<StringEntered> {
    where { system(WindowSystem::class).hasWindow(entity, FriendsChatSetup) }
    then {
        GameServer.worldSession?.write(FriendsChatName(entity, string))
    }
}

on<WindowInteraction> {
    where { target == FriendsChatSetup }
    then {
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
