package worlds.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.annotations.EntityId
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class RunToggled : Component() {
    @EntityId
    @JvmField
    var entity = -1
}