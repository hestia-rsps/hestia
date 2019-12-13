package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class NamePrefix() : Component() {

    constructor(prefix: String) : this() {
        this.prefix = prefix
    }

    var prefix: String? = ""
}