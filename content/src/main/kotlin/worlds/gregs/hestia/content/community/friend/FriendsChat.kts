package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatSetupRequest
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FriendsChat

on(InterfaceOption, "Join Chat", id = FriendsChat) { _, _, _, _ ->
}

on(InterfaceOption, "Open Settings", id = FriendsChat) { _, _, _, _ ->
    GameServer.worldSession?.write(FriendsChatSetupRequest(entity))
}

on(InterfaceOption, "Toggle-LootShare", id = FriendsChat) { _, _, _, _ ->
}

on(InterfaceOption, "Kick/ban", id = FriendsChat) { _, _, _, _ ->
}