package worlds.gregs.hestia.core.display.request.model.components

import com.artemis.Component
import com.artemis.annotations.EntityId
import worlds.gregs.hestia.core.entity.entity.model.components.Position

class Assistance : Component() {
    @EntityId
    @JvmField
    var helper = -1

    var point = Position()
}