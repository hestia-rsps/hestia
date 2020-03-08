package worlds.gregs.hestia.content.community.friend

import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import world.gregs.hestia.core.network.protocol.messages.FriendsChatSettings
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.FriendsChatSetupOpen
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FriendsChatSetup
import worlds.gregs.hestia.core.display.interfaces.logic.systems.InterfaceSystem
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.network.client.encoders.messages.Script
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.game

on<FriendsChatSetupOpen> {
    then {
        val es = game.getSystem(net.mostlyoriginal.api.event.common.EventSystem::class)
        es.perform(entity, OpenInterface(FriendsChatSetup))
    }
}

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

on(InterfaceOption, "Set prefix", id = FriendsChatSetup) { _, _, _, _ ->
    entity send Script(109, "Enter chat prefix:")
}

on(InterfaceOption, "Disable", id = FriendsChatSetup) { _, _, _, _ ->
    GameServer.worldSession?.write(FriendsChatName(entity, ""))
}

val options = arrayOf("No-one", "Anyone", "Any friends", "Recruit+", "Corporal+", "Sergeant+", "Lieutenant+", "Captain+", "General+", "Only me")
options.forEach { optionName ->
    on(InterfaceOption, optionName, id = FriendsChatSetup) { hash, _, _, option ->
        GameServer.worldSession?.write(FriendsChatSettings(entity, hash, option))
    }
}