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

/**
 * Handles sending and accepting responses of [Request]'s
 * TODO When should old requests be removed?
 * TODO rethink. do a flow chart. this is simple.
 */
class RequestSystem : Requests() {

    private lateinit var requestListMapper: ComponentMapper<RequestList>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var es: EventSystem

    override fun sendRequest(entityId: Int, target: Int, request: Request) {
        val requestList = requestListMapper.get(entityId)
        val targetName = displayNameMapper.get(target).name!!
        val name = displayNameMapper.get(entityId).name!!
        //If target requested recently
        if(hasRequest(target, entityId, request)) {
            respond(entityId, target, request)//Skip to response
        } else {
            requestList.requests[targetName] = request
            es.send(entityId, Chat(request.otherType, 0, null, request.sendRequest))
            es.send(target, Chat(request.reqType, 0, name, request.receiveRequest))
        }
    }

    override fun respond(entityId: Int, target: Int, request: Request) {
        es.dispatch(RequestResponse(target, entityId, request))
    }

    override fun accept(entityId: Int, target: Int, request: Request) {
        es.send(entityId, Chat(request.otherType, 0, null, request.sendResponse))
        es.dispatch(AcceptedRequest(entityId, target, request))
    }

    override fun hasRequest(entityId: Int, target: Int, request: Request): Boolean {
        val requestList = requestListMapper.get(entityId)
        val displayName = displayNameMapper.get(target)
        return requestList.requests.containsKey(displayName.name)
    }
}

infix fun Task.request(request: Request) {
    getSystem(RequestSystem::class).sendRequest(entity, target, request)
}

infix fun Task.accept(request: Request) {
    getSystem(RequestSystem::class).accept(entity, target, request)
}