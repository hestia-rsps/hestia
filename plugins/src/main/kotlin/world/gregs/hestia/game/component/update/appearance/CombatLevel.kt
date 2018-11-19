package world.gregs.hestia.game.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class CombatLevel : Component() {
    var level: Int = 1
}