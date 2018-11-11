package world.gregs.hestia.game.component.map

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class RegionMap: Component() {
    val regionId = -1
    var masks: Array<Array<IntArray>> = Array(4) { _ -> Array(64) { IntArray(64) } }
}