package worlds.gregs.hestia.core.display.variable.model.variable

import worlds.gregs.hestia.core.display.variable.api.Variable

abstract class BitwiseVar<T> : Variable<Int> {
    abstract fun getValue(id: T): Int?
}