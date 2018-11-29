package worlds.gregs.hestia.game.plugins.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Region : Component() {
    var id: Int = -1
    var loadStage: Int = 0

    val x: Int
        get() = (id shr 8) * 64

    val y: Int
        get() = (id and 0xff) * 64

}