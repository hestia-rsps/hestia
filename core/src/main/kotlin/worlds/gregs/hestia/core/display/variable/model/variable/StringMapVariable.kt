package worlds.gregs.hestia.core.display.variable.model.variable

import worlds.gregs.hestia.core.display.variable.api.Variable

data class StringMapVariable(override val id: Int, override val type: Variable.Type, override val persistent: Boolean = false, val values: Map<String, Int>, override val defaultValue: String = values.keys.first()) : Variable<String> {

    init {
        check(values.containsKey(defaultValue)) { "Values must contain default '$defaultValue'" }
    }

    override fun toInt(value: String): Int {
        return values[value] ?: super.toInt(value)
    }
}