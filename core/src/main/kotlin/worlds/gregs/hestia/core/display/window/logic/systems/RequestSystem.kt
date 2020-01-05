package worlds.gregs.hestia.core.display.window.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.protocol.encoders.messages.Chat
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.window.api.Requests
import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.display.window.model.components.RequestList
import worlds.gregs.hestia.core.display.window.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.window.model.events.RequestResponse
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.entity
import worlds.gregs.hestia.core.task.api.event.target
import worlds.gregs.hestia.core.task.api.getSystem
import worlds.gregs.hestia.game.Engine

/**
 * Handles sending and accepting responses of [Request]'s
 */
class RequestSystem : Requests() {

    private lateinit var requestListMapper: ComponentMapper<RequestList>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var es: EventSystem

    override fun sendRequest(entityId: Int, target: Int, request: Request) {
        val requestList = requestListMapper.get(entityId)
        val targetName = displayNameMapper.get(target).name!!
        val name = displayNameMapper.get(entityId).name!!
        //Log request
        if(!requestList.requests.containsKey(targetName)) {
            requestList.requests[targetName] = mutableMapOf()
        }
        requestList.requests[targetName]!![request] = Engine.ticks

        //If target requested recently
        if(hasRequest(target, entityId, request)) {
            acceptRequest(entityId, target, request)//Skip to response
        } else {
            es.send(entityId, Chat(request.otherType, 0, null, request.sendRequest))
            es.send(target, Chat(request.reqType, 0, name, request.receiveRequest))
        }
    }

    override fun acceptRequest(entityId: Int, target: Int, request: Request) {
        if(request.sendResponse != null) {
            es.send(entityId, Chat(request.otherType, 0, null, request.sendResponse))
        }
        es.dispatch(RequestResponse(target, entityId, request))
        es.dispatch(AcceptedRequest(entityId, target, request))
        purgeRequests(entityId, target, request)
    }

    internal fun purgeRequests(entityId: Int, target: Int, request: Request) {
        val requestList = requestListMapper.get(entityId)
        val targetRequestList = requestListMapper.get(target)
        val targetName = displayNameMapper.get(target).name!!
        val name = displayNameMapper.get(entityId).name!!
        requestList.requests[targetName]?.remove(request)
        targetRequestList.requests[name]?.remove(request)
    }

    override fun hasRequest(entityId: Int, target: Int, request: Request): Boolean {
        val requestList = requestListMapper.get(entityId)
        val displayName = displayNameMapper.get(target)
        val requests = requestList.requests[displayName.name]
        return requests != null && requests.containsKey(request) && Engine.ticks - requests[request]!! < THIRTY_SECONDS//Actual functionality is unknown
    }

    companion object {
        private const val THIRTY_SECONDS = 50
    }
}

infix fun Task.request(request: Request) {
    getSystem(RequestSystem::class).sendRequest(entity, target, request)
}