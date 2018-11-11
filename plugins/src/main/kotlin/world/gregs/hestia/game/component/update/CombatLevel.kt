package world.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class CombatLevel : Component() {
    val level: Int = 1
}