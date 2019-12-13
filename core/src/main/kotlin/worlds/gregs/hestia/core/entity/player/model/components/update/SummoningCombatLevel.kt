package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class SummoningCombatLevel() : Component() {

    constructor(level: Int) : this() {
        this.level = level
    }

    var level: Int = 1
}