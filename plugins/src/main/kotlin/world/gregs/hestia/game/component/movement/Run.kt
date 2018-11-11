package world.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Run : Component() {
    var direction = -1
}