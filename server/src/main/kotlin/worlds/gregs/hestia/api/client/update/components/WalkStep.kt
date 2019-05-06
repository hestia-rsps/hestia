package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.client.update.block.Direction

@PooledWeaver
class WalkStep : Component() {
    var direction = Direction.NONE
}