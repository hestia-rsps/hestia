package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.api.Map
import worlds.gregs.hestia.core.world.region.api.Regions

lateinit var map: Map
lateinit var regions: Regions

on<Command> {
    where { prefix == "test" }
    fun Command.task() = queue(2) {
        val position = entity.get(Position::class)
        val regionId = position.regionId
        val clipping = map.getClipping(regions.getEntityId(regionId))!!
        println("Collision mask ${position.x}, ${position.y} - ${clipping.getMask(position.xInRegion, position.yInRegion, position.plane)}")
    }
    then(Command::task)
}
