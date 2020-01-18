package worlds.gregs.hestia.core.display.variable.model.variable

import worlds.gregs.hestia.core.display.variable.api.Variable

data class StringVariable(override val id: Int, override val type: Variable.Type, override val persistent: Boolean = false, override val defaultValue: String = "") : Variable<String>