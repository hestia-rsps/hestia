package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.artemis.players
import worlds.gregs.hestia.artemis.toArray
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.item.floor.logic.systems.FloorItemSystem
import worlds.gregs.hestia.core.entity.item.floor.model.events.FloorItemOption
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.game

on<Command> {
    where { prefix == "take" }
    then {
        val bot = game.players().toArray().first()
        val floorItem = game.getSystem(FloorItemSystem::class.java).getItems()
        val item = floorItem.first()
        bot perform FloorItemOption(item, "Take")
        isCancelled = true
    }
}