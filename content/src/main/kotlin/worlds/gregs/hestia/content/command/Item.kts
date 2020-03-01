package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.add
import worlds.gregs.hestia.core.script.on

on<Command> {
    where { prefix == "item" || prefix == "pickup" }
    then {
        val parts = content.split(" ")
        val itemId = parts[0].toInt()
        val amount = if (parts.size > 1) parts[1].toInt() else 1
        val result = entity inventory add(itemId, amount)
        if (result !is ItemResult.Success) {
            entity perform Chat("Your inventory is full.")
        }
        isCancelled = true
    }
}