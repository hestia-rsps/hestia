package worlds.gregs.hestia.core.display.request.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.request.model.Request
import worlds.gregs.hestia.core.display.request.model.components.RequestList

abstract class Requests : SubscriptionSystem(Aspect.all(RequestList::class)) {

    /**
     * Sends a request to [target] accepting if [target] recently sent the same requests to [entityId]
     * @param entityId The entity sending the request
     * @param target The entity to send the request too
     * @param request The type of request to send
     */
    abstract fun sendRequest(entityId: Int, target: Int, request: Request)

    /**
     * Accepts a request sent from [target]
     * @param entityId The entity who sent the request
     * @param target The entity who received the request
     * @param request The type of request sent
     */
    abstract fun acceptRequest(entityId: Int, target: Int, request: Request)

    /**
     * Checks if [target] has a request from [entityId]
     * @param entityId The entity who's request to check for
     * @param target The entity to check has the request
     * @param request The type of request to check for
     */
    abstract fun hasRequest(entityId: Int, target: Int, request: Request): Boolean

}