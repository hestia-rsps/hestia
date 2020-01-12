package worlds.gregs.hestia.core.display.window.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class VariableStore : Component() {
    val values = mutableMapOf<Int, Int>()
}