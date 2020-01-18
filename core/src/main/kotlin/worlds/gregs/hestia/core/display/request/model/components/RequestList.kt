package worlds.gregs.hestia.core.display.request.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.display.request.model.Request

class RequestList : Component() {
    val requests = mutableMapOf<String, MutableMap<Request, Int>>()
}