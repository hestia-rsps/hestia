package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatSetupRequest
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FriendsChat
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction

on<InterfaceInteraction> {
    where { id == FriendsChat }
    then {
        when(component) {
            27 -> {//Join/Leave
            }
            33 -> {//Settings
                GameServer.worldSession?.write(FriendsChatSetupRequest(entity))
            }
            19 -> {//Toggle loot share
            }
            21 -> {//Kick/Ban
            }
        }
    }
}