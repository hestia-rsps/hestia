package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatSetupRequest
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsChat
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == FriendsChat }
    task {
        val (_, _, component) = event(this)
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