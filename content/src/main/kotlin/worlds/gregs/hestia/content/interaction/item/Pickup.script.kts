package worlds.gregs.hestia.content.interaction.item

import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.events.FloorItemOption
import worlds.gregs.hestia.core.task.api.event.target

on<FloorItemOption> {
    where { option == "Take" }
    task(TaskPriority.High) {
        entity distance 0 interact target

        //Get floor item info
        val amount = target get Amount::class
        val type = target get Type::class

        //Attempt to add to inventory
        when(entity overflow false transform add(type.id, amount.amount)) {
            is ItemResult.Success -> {
                if (entity notSamePosition target) {
                    entity animate 832//or 3864
                }
                //Remove floor item
                world delete target
            }
            is ItemResult.Issue ->
                entity message "You don't have enough inventory space to hold that item."
        }
    }
}