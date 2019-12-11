package worlds.gregs.hestia.core.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class SummoningCombatLevel() : Component() {

    constructor(level: Int) : this() {
        this.level = level
    }

    var level: Int = 1
}