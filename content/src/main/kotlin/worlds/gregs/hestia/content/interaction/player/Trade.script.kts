package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TradeMain
import worlds.gregs.hestia.core.display.window.logic.systems.request
import worlds.gregs.hestia.core.display.window.model.PlayerOptions.TRADE
import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.core.display.window.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.window.model.events.PlayerOption
import worlds.gregs.hestia.core.display.window.model.events.RequestResponse
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameTrade
import worlds.gregs.hestia.core.task.api.event.target

on<PlayerOption> {
    where { option == TRADE }
    task(TaskPriority.High) {
        entity distance 1 interact target
        request(Request.TRADE)
    }
}

//The original requester
on<RequestResponse> {
    where { request == Request.TRADE }
    task(TaskPriority.High) {
        trade()
    }
}

//The responder
on<AcceptedRequest> {
    where { request == Request.TRADE }
    task(TaskPriority.High) {
        trade()
    }
}

fun Task.trade() {
    onCancel {
        entity closeWindow WindowPane.MAIN_SCREEN
        target closeWindow WindowPane.MAIN_SCREEN
        entity type GameTrade message "Trade declined."
        target type GameTrade message "Other player declined trade."
    }
    entity openWindow TradeMain
}