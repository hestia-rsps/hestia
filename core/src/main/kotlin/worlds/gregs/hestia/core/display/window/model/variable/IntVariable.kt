package worlds.gregs.hestia.core.display.window.model.variable

import worlds.gregs.hestia.core.display.window.api.Variable

data class IntVariable(override val id: Int, override val type: Variable.Type, override val persistent: Boolean = false, override val defaultValue: Int = 0) : Variable<Int> {
    override fun getInt(value: Int): Int {
        return value
    }

    override fun getValue(value: Int): Int {
        return value
    }
}