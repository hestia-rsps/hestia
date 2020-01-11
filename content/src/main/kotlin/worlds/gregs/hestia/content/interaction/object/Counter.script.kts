package worlds.gregs.hestia.content.interaction.`object`

import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.`object`.model.events.ObjectOption
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.logic.systems.RouteSuspension
import worlds.gregs.hestia.core.task.logic.systems.TickSuspension
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem.Companion.isNear
import worlds.gregs.hestia.core.world.movement.model.events.Interact
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

on<ObjectOption> {
    where { option == "Use" && name == "Counter" }
    fun ObjectOption.task() = queue(TaskPriority.High) {
        entity perform Interact(target, true)

        val route = await(RouteSuspension())
        val definition = system(ObjectDefinitionSystem::class).get(target.get(GameObject::class).id)
        val canInteract = route.steps >= 0 && !route.alternative || isNear(entity get Position::class, target get Position::class, definition.sizeX, definition.sizeY, true)
        await(TickSuspension(1))
        if(!canInteract) {
            entity perform Face(target get Position::class)//TODO object def size
            entity perform Chat("You can't reach that.")
            return@queue
        }
        TODO("Open bank")
    }
    then(ObjectOption::task)
}