package worlds.gregs.hestia.game.plugins.core.components.entity

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Size : Component() {
    var sizeX = 1
    var sizeY = 1
}