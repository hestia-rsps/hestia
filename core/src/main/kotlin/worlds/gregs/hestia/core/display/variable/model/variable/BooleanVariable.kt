package worlds.gregs.hestia.core.display.variable.model.variable

import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.core.display.variable.api.Variable

data class BooleanVariable(override val id: Int, override val type: Variable.Type, override val persistent: Boolean = false, override val defaultValue: Boolean = false) : Variable<Boolean> {
    override fun toInt(value: Boolean): Int {
        return value.int
    }
}