package worlds.gregs.hestia.api.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class DynamicRegion : Component() {
    val regionData = HashMap<Int, Int>()
    val reloads = ArrayList<Int>()
}