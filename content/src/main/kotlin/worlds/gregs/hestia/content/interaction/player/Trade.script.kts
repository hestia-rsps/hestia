package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.player.model.events.PlayerOption
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameTrade
import worlds.gregs.hestia.core.script.dsl.task.ChatType.Trade
import worlds.gregs.hestia.core.script.dsl.task.PlayerOptions.TRADE
import worlds.gregs.hestia.core.task.api.event.target

on<PlayerOption> {
    where { option == TRADE }
    task(TaskPriority.High) {
        entity distance 1 interact target
        entity type GameTrade message "Sending trade offer..."
        val display = (entity get DisplayName::class).name!!
        target type Trade name display message "wishes to trade with you."
    }
}