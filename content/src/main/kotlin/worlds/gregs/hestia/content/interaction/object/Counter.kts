package worlds.gregs.hestia.content.interaction.`object`

import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.ObjectOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.task.model.await.Route
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem.Companion.isNear
import worlds.gregs.hestia.core.world.movement.model.events.Interact
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

on(ObjectOption, "Use", "Counter") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity perform Interact(target)

        val route = await(Route())
        val definition = system(ObjectDefinitionSystem::class).get(target.get(GameObject::class).id)
        val canInteract = route.steps >= 0 && !route.partial || isNear(entity get Position::class, target get Position::class, definition.sizeX, definition.sizeY, true)
        await(Ticks(1))
        if (!canInteract) {
            entity perform Face(target get Position::class)//TODO object def size
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }
        TODO("Open bank")
    }
    then(EntityActions::task)
}