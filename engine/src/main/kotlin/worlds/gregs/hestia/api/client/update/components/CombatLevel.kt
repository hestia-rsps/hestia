package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class CombatLevel() : Component() {

    constructor(level: Int) : this() {
        this.level = level
    }

    var level: Int = 1
}