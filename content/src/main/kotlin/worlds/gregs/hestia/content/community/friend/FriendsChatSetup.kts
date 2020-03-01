package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FriendsChatSetup
import worlds.gregs.hestia.core.display.interfaces.logic.systems.InterfaceSystem
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.network.client.encoders.messages.Script
import worlds.gregs.hestia.core.script.on

on<InterfaceOpened> {
    where { id == FriendsChatSetup }
    then {
        //Send details
        GameServer.worldSession?.write(FriendsChatSettings(entity, FriendsChatSetup shl 16, 0))
    }
}

//Chat prefix
on<StringEntered> {
    where { system(InterfaceSystem::class).hasInterface(entity, FriendsChatSetup) }
    then {
        GameServer.worldSession?.write(FriendsChatName(entity, string))
    }
}

on<InterfaceInteraction> {
    where { id == FriendsChatSetup }
    then {
        when(component) {
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
                GameServer.worldSession?.write(FriendsChatSettings(entity, FriendsChatSetup shl 16 or component, option))
            }
//            3 -> {}//Close
        }
    }
}
