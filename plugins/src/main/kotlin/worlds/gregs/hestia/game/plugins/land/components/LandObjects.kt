package worlds.gregs.hestia.game.plugins.land.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class LandObjects : Component() {
    val list = HashMap<Int, ArrayList<Int>>()
}