package worlds.gregs.hestia.core.entity.`object`.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ObjectType : Component() {
    var type = -1
}