package worlds.gregs.hestia.api.`object`

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class GameObject : Component() {
    var id = -1
}