package worlds.gregs.hestia.core.display.variable.model.variable

import worlds.gregs.hestia.core.display.variable.api.Variable

data class IntVariable(override val id: Int, override val type: Variable.Type, override val persistent: Boolean = false, override val defaultValue: Int = 0) : Variable<Int> {
    override fun toInt(value: Int): Int {
        return value
    }
}