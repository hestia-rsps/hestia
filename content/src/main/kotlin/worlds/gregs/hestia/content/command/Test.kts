package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.item.container.logic.clear
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.script.on

on<Command> {
    where { prefix == "test" }
    fun Command.task() = queue(2) {
        val equipment = entity container ContainerType.EQUIPMENT
        println("val $content = arrayOf(${equipment.filterNotNull().map { it.type }.joinToString()})")
    }
    then(Command::task)
}

on<Command> {
    where { prefix == "clear" }
    fun Command.task() = queue(2) {
        ContainerType.EQUIPMENT transform clear()
        entity perform UpdateAppearance()
    }
    then(Command::task)
}
