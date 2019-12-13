package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class DisplayName() : Component() {

    constructor(name: String) : this() {
        this.name = name
    }

    var name: String? = null
}