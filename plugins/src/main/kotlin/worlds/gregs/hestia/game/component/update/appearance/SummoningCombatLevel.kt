package worlds.gregs.hestia.game.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class SummoningCombatLevel : Component() {
    var level: Int = 1
}