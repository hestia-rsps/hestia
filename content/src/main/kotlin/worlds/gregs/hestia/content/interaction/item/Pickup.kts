package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.FloorItemOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.entity.model.events.Animation
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.add
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.task.model.await.Route
import worlds.gregs.hestia.core.task.model.await.Ticks
import worlds.gregs.hestia.core.world.movement.logic.systems.calc.StepBesideSystem.Companion.isNear
import worlds.gregs.hestia.core.world.movement.model.events.Interact

on(FloorItemOption, "Take") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        entity perform Interact(target, true)

        val route = await(Route())
        val canInteract = route.steps >= 0 && !route.alternative || isNear(entity get Position::class, target get Position::class, 1, 1, true)
        await(Ticks(1))
        if (!canInteract) {
            entity perform Face(target get Position::class)
            entity perform Chat("You can't reach that.")
            return@strongQueue
        }

        //Get floor item info
        val amount = target get Amount::class
        val type = target get Type::class

        //Attempt to add to inventory
        when (entity inventory add(type.id, amount.amount)) {
            is ItemResult.Success -> {
                if (!entity.get(Position::class).same(target.get(Position::class))) {
                    entity perform Animation(832)//or 3864
                }
                //Remove floor item
                world.delete(target)
            }
            is ItemResult.Issue ->
                entity perform Chat("You don't have enough inventory space to hold that item.")
        }
    }
    then(EntityActions::task)
}