package worlds.gregs.hestia.core.display.window.model.variable

import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.core.display.window.api.Variable

@Suppress("DataClassPrivateConstructor")
data class BooleanVariable private constructor(override val id: Int, override val type: Variable.Type, override val persistent: Boolean, override val defaultValue: Int) : Variable<Boolean> {
    constructor(id: Int, type: Variable.Type, persistent: Boolean = false, defaultValue: Boolean = false) : this(id, type, persistent, defaultValue.int)

    override fun getInt(value: Boolean): Int {
        return value.int
    }

    override fun getValue(value: Int): Boolean {
        return value == 1
    }
}