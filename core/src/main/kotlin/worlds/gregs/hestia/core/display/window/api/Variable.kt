package worlds.gregs.hestia.core.display.window.api

import worlds.gregs.hestia.core.display.window.logic.systems.VariableSystem.Companion.names
import worlds.gregs.hestia.core.display.window.logic.systems.VariableSystem.Companion.variables
import worlds.gregs.hestia.core.display.window.model.variable.BooleanVariable
import worlds.gregs.hestia.core.display.window.model.variable.IntVariable
import worlds.gregs.hestia.core.display.window.model.variable.StringVariable

interface Variable<T> {
    val id: Int
    val defaultValue: Int
    val type: Type
    val persistent: Boolean

    val hash: Int
        get() = toHash(id, type)

    fun getInt(value: T): Int

    fun getValue(value: Int): T

    enum class Type {
        VARBIT, VARP, VARC
    }

    fun register(name: String) {
        names[name] = hash
        register()
    }


    fun register() {
        variables[hash] = this
    }

    companion object {

        fun toHash(id: Int, type: Type): Int {
            return type.ordinal + (id shl 2)
        }

        fun idFrom(hash: Int): Int {
            return hash shr 2
        }

        fun typeFrom(hash: Int): Type {
            return Type.values()[hash and 0x4]
        }
    }

}