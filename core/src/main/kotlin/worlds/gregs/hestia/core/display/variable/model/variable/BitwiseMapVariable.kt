package worlds.gregs.hestia.core.display.variable.model.variable

import worlds.gregs.hestia.core.display.variable.api.Variable

data class BitwiseMapVariable<T>(override val id: Int, override val type: Variable.Type, override val persistent: Boolean = false, override val defaultValue: Int = 0, val values: Map<T, Int>) : BitwiseVar<T>() {
    override fun toInt(value: Int): Int {
        return value
    }

    override fun getValue(id: T): Int? {
        return values[id]
    }
}