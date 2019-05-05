package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class DisplayName() : Component() {

    constructor(name: String) : this() {
        this.name = name
    }

    var name: String? = null
}