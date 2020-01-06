package worlds.gregs.hestia.content.command

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.update.model.components.UpdateMovement
import worlds.gregs.hestia.core.world.movement.model.components.RunToggled

lateinit var runToggledMapper: ComponentMapper<RunToggled>
lateinit var updateMovementMapper: ComponentMapper<UpdateMovement>

on<Command> {
    where { prefix == "run" }
    then {
        runToggledMapper.set(entity, !runToggledMapper.has(entity))
        updateMovementMapper.create(entity)
        isCancelled = true
    }
}