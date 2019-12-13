package worlds.gregs.hestia.core.entity.`object`.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class GameObject : Component() {
    var id = -1
}