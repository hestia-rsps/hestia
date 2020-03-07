package worlds.gregs.hestia.content.command

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.players
import worlds.gregs.hestia.artemis.toArray
import worlds.gregs.hestia.core.action.model.ActionContext
import worlds.gregs.hestia.core.action.logic.systems.FloorItemOptionSystem
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.item.floor.logic.systems.FloorItemSystem
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.game

lateinit var options: FloorItemOptionSystem
lateinit var actionContextMapper: ComponentMapper<ActionContext>

on<Command> {
    where { prefix == "take" }
    then {
        val bot = game.players().toArray().first()
        val floorItem = game.getSystem(FloorItemSystem::class.java).getItems()
        val item = floorItem.first()
        val action = options.get("Take", item)
        val context = actionContextMapper.get(entity)
        action?.invoke(context, item)
        isCancelled = true
    }
}