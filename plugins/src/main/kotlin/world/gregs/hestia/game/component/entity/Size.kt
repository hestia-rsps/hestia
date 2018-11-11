package world.gregs.hestia.game.component.entity

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Size : Component() {
    var sizeX = 1
    var sizeY = 1
}