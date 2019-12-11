package worlds.gregs.hestia.core.plugins.`object`.component

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ObjectType : Component() {
    var type = -1
}