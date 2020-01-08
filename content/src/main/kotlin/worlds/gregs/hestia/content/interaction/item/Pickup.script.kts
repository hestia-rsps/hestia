package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.entity.model.events.Animate
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.events.FloorItemOption

on<FloorItemOption> {
    where { option == "Take" }
    fun FloorItemOption.task() = queue(TaskPriority.High) {
        entity.interact(target, 0)

        //Get floor item info
        val amount = target get Amount::class
        val type = target get Type::class

        //Attempt to add to inventory
        when(entity overflow false transform add(type.id, amount.amount)) {
            is ItemResult.Success -> {
                if (!entity.get(Position::class).same(target.get(Position::class))) {
                    entity perform(Animate(832))//or 3864
                }
                //Remove floor item
                world.delete(target)
            }
            is ItemResult.Issue ->
                entity perform Chat("You don't have enough inventory space to hold that item.")
        }
    }
    then(FloorItemOption::task)
}