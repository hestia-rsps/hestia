package worlds.gregs.hestia.core.world.land.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class LandObjects : Component() {
    val list = HashMap<Int, ArrayList<Int>>()
}