package worlds.gregs.hestia.core.display.window.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.display.window.model.FilterMode

class Assisting : Component() {
    var timeout = 0L//TODO consider blackboard for these which are temp?
    var lastRequest = 0
    var mode: FilterMode = FilterMode.On
}