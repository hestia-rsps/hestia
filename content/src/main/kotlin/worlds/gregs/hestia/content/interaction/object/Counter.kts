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
        TODO("Open bank")
    }
    then(EntityActions::task)
}