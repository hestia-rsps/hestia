package worlds.gregs.hestia.content.interaction.`object`

import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.`object`.model.events.ObjectOption
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.logic.systems.RouteSuspension
import worlds.gregs.hestia.core.task.logic.systems.TickSuspension
import worlds.gregs.hestia.core.world.movement.model.components.calc.Route

on<ObjectOption> {
    where { option == "Use" && name == "Counter" }
    fun ObjectOption.task() = queue(TaskPriority.High) {
        val route = entity create Route::class
        route.entityId = target
        route.alternative = true

        val route2 = await(RouteSuspension())
        val interact = canInteract(route2, entity get Position::class, target get Position::class, target)
        await(TickSuspension(1))
        if(!interact) {
            val position = target.get(Position::class)
            entity.get(Face::class).apply {
                x = position.x
                y = position.y
            }
            entity create Facing::class
            entity perform Chat("You can't reach that.")
            return@queue
        }
        TODO("Open bank")
    }
    then(ObjectOption::task)
}