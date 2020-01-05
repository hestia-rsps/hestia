package worlds.gregs.hestia.core.display.window.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.window.model.Request
import worlds.gregs.hestia.core.display.window.model.components.RequestList

abstract class Requests : SubscriptionSystem(Aspect.all(RequestList::class)) {

    abstract fun sendRequest(entityId: Int, target: Int, request: Request)

    abstract fun acceptRequest(entityId: Int, target: Int, request: Request)

    abstract fun hasRequest(entityId: Int, target: Int, request: Request): Boolean

}