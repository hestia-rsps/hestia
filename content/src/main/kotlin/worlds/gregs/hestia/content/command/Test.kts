package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.player.model.components.update.HeadIcon
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.region.api.Regions

lateinit var map: Map
lateinit var regions: Regions

on<Command> {
    where { prefix == "test" }
    fun Command.task() = queue(2) {

        val headIcon = entity create HeadIcon::class
        headIcon.headIcon = content.toInt()
        entity perform UpdateAppearance()
    }
    then(Command::task)
}
