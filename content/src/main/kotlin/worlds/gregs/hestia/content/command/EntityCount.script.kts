package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.artemis.players
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.mobs

on<Command> {
    where { prefix == "players" }
    then {
        println(game.players().size())
        isCancelled = true
    }
}

on<Command> {
    where { prefix == "mobs" }
    then {
        println(world.mobs().size())
        isCancelled = true
    }
}