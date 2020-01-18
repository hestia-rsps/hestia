package worlds.gregs.hestia.core.display.variable.model

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class VariableStore : Component() {
    val values = mutableMapOf<Int, Any>()
}