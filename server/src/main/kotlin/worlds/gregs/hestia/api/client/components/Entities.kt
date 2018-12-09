package worlds.gregs.hestia.api.client.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
abstract class Entities : Component() {
    var list = ArrayList<Int>()
}