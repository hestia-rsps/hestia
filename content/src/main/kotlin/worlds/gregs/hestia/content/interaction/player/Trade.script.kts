package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.window.logic.systems.accept
import worlds.gregs.hestia.core.display.window.logic.systems.request
import worlds.gregs.hestia.core.display.window.model.PlayerOptions.TRADE
import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.display.window.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.window.model.events.PlayerOption
import worlds.gregs.hestia.core.display.window.model.events.RequestResponse
import worlds.gregs.hestia.core.task.api.event.target

on<PlayerOption> {
    where { option == TRADE }
    task(TaskPriority.High) {
        entity distance 1 interact target
        request(Request.TRADE)
    }
}

on<RequestResponse> {
    where { request == Request.ASSIST }
    task(TaskPriority.High) {
        entity distance 1 interact target
        accept(Request.TRADE)
        entity message "You are now trading with ${target.getName()}."//Temp
        //Open trade screen
    }
}

on<AcceptedRequest> {
    where { request == Request.TRADE }
    task {
        //Open trade screen
        entity message "You are also now trading with ${target.getName()}."//Temp
    }
}