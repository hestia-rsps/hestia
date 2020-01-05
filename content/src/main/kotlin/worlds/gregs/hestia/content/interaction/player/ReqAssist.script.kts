package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.window.logic.systems.request
import worlds.gregs.hestia.core.display.window.model.PlayerOptions.ASSIST
import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.display.window.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.window.model.events.PlayerOption
import worlds.gregs.hestia.core.display.window.model.events.RequestResponse
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameAssist
import worlds.gregs.hestia.core.task.api.event.target

on<PlayerOption> {
    where { option == ASSIST }
    task(TaskPriority.High) {
        entity distance 1 interact target
        request(Request.ASSIST)
    }
}

on<RequestResponse> {
    where { request == Request.ASSIST }
    task(TaskPriority.High) {
        entity distance 1 interact target
        entity message "You are assisting ${target.getName()}."
        entity message "It has been 24 hours since you first helped someone using the Assist System."
        entity message "You can now use it to gain the full amount of XP."
    }
}
on<AcceptedRequest> {
    where { request == Request.ASSIST }
    task {
        entity type GameAssist message "You are being assisted by ${target.getName()}."
    }
}