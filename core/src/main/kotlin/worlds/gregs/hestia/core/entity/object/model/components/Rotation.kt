package worlds.gregs.hestia.core.entity.`object`.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Rotation : Component() {
    var rotation = -1
}