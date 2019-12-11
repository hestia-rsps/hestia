package worlds.gregs.hestia.core.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class NamePrefix() : Component() {

    constructor(prefix: String) : this() {
        this.prefix = prefix
    }

    var prefix: String? = ""
}