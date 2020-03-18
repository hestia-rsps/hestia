package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.action.model.EntityActions
import worlds.gregs.hestia.core.action.model.PlayerOption
import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.dialogue.model.ChatType.GameTrade
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TradeMain
import worlds.gregs.hestia.core.display.request.logic.RequestSystem
import worlds.gregs.hestia.core.display.request.model.Request
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.interfaces.model.events.request.CloseWindow
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.request.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.request.model.events.RequestResponse
import worlds.gregs.hestia.core.task.model.await.WithinRange
import worlds.gregs.hestia.core.world.movement.model.events.Follow
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.task.api.Task

on(PlayerOption, "Trade") { ->
    fun EntityActions.task(target: Int) = strongQueue {
        system(RequestSystem::class).sendRequest(entity, target, Request.TRADE)
    }
    then(EntityActions::task)
}

//The original requester
on<RequestResponse> {
    where { request == Request.TRADE }
    fun RequestResponse.task() = strongQueue {
        trade(this, target)
    }
    then(RequestResponse::task)
}

//The responder
on<AcceptedRequest> {
    where { request == Request.TRADE }
    fun AcceptedRequest.task() = strongQueue {
        trade(this, target)
    }
    then(AcceptedRequest::task)
}

fun EntityAction.trade(task: Task, target: Int) {
    task.onCancel {
        entity perform CloseWindow(Window.MAIN_SCREEN)
        target perform CloseWindow(Window.MAIN_SCREEN)
        entity perform Chat("Trade declined.", GameTrade)
        target perform Chat("Other player declined trade.", GameTrade)
    }
    entity perform OpenInterface(TradeMain)
}