package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class CombatLevel() : Component() {

    constructor(level: Int) : this() {
        this.level = level
    }

    var level: Int = 1
}