package worlds.gregs.hestia.game.plugins.movement.components.types

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class RunStep : Component() {
    var direction = -1
}