package worlds.gregs.hestia.game.plugins.`object`.component

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ObjectType : Component() {
    var type = -1
}