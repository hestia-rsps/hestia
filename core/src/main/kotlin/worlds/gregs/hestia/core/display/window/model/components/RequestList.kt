package worlds.gregs.hestia.core.display.window.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.display.window.model.Request

class RequestList : Component() {
    val requests = mutableMapOf<String, MutableMap<Request, Int>>()
}