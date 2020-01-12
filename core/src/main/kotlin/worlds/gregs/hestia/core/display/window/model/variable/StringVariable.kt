package worlds.gregs.hestia.core.display.window.model.variable

import worlds.gregs.hestia.core.display.window.api.Variable

@Suppress("DataClassPrivateConstructor")
data class StringVariable private constructor(override val id: Int, override val type: Variable.Type, override val persistent: Boolean, override val defaultValue: Int, private val values: Map<Int, String>) : Variable<String> {
    constructor(id: Int, type: Variable.Type, persistent: Boolean = false, defaultValue: String, values: Map<Int, String>) :
            this(id, type, persistent, values.getValue(defaultValue) ?: throw IllegalArgumentException("Values must contain default '$defaultValue'"), values)

    override fun getInt(value: String): Int {
        return values.getValue(value) ?: -1
    }

    override fun getValue(value: Int): String {
        return values.getOrDefault(value, values[defaultValue]!!)
    }

    companion object {
        private fun Map<Int, String>.getValue(value: String): Int? = entries.firstOrNull { it.value == value }?.key
    }
}