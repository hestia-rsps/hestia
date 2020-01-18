package worlds.gregs.hestia.core.display.request.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.request.model.components.RequestList
import worlds.gregs.hestia.core.display.request.model.Request

abstract class Requests : SubscriptionSystem(Aspect.all(RequestList::class)) {

    abstract fun sendRequest(entityId: Int, target: Int, request: Request)

    abstract fun acceptRequest(entityId: Int, target: Int, request: Request)

    abstract fun hasRequest(entityId: Int, target: Int, request: Request): Boolean

}