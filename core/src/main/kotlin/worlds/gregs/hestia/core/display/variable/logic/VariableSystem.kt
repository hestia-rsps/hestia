package worlds.gregs.hestia.core.display.variable.logic

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.variable.model.VariableStore
import worlds.gregs.hestia.core.display.variable.model.events.*
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVar
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVariable
import worlds.gregs.hestia.network.client.encoders.messages.Varbit
import worlds.gregs.hestia.network.client.encoders.messages.Varc
import worlds.gregs.hestia.network.client.encoders.messages.VarcStr
import worlds.gregs.hestia.network.client.encoders.messages.Varp
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

@Suppress("UNCHECKED_CAST")
class VariableSystem : Variables() {

    private lateinit var variableStoreMapper: ComponentMapper<VariableStore>
    private lateinit var es: EventSystem

    override fun removed(entityId: Int) {
        val store = variableStoreMapper.get(entityId) ?: return
        store.values.forEach { (hash, value) ->
            val variable = variables[hash]!!
            if (variable.persistent) {
                println("Save ${names.entries.firstOrNull { it.value == hash }} $value")
            }
        }
    }

    override fun <T : Any> set(entityId: Int, key: String, value: T, refresh: Boolean) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] as? Variable<T> ?: return logger.warn("Cannot find variable for key '$key'")
        val changed = store.set(variable, value)
        if (changed && refresh) {
            send(entityId, key)
        }
    }

    override fun send(entityId: Int, key: String) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] ?: return logger.warn("Cannot find variable for key '$key'")
        variable.send(entityId, store)
    }

    override fun <T : Any> get(entityId: Int, key: String, default: T): T {
        return get(entityId, key) ?: default
    }

    override fun <T : Any> get(entityId: Int, key: String): T? {
        val store = variableStoreMapper.get(entityId) ?: return null
        val variable = variables[key] as? Variable<T> ?: return null
        return store.get(variable)
    }

    override fun <T : Any> add(entityId: Int, key: String, id: T, refresh: Boolean) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] as? BitwiseVar<T> ?: return logger.warn("Cannot variable for key '$key'")

        val operand = variable.getValue(id) ?: return logger.warn("Invalid bitwise value '$id'")
        val value = store.get(variable)

        if (!value.has(operand)) {//If isn't already added
            val changed = store.set(variable, value + operand)//Add
            if (changed && refresh) {
                send(entityId, key)
            }
        }
    }

    override fun <T : Any> remove(entityId: Int, key: String, id: T, refresh: Boolean) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] as? BitwiseVariable<T> ?: return logger.warn("Cannot find variable for key '$key'")

        val operand = variable.getValue(id) ?: return logger.warn("Invalid bitwise value '$id'")
        val value = store.get(variable)

        if (value.has(operand)) {//If is added
            val changed = store.set(variable, value - operand)//Remove
            if (changed && refresh) {
                send(entityId, key)
            }
        }
    }

    override fun <T : Any> has(entityId: Int, key: String, id: T): Boolean {
        val store = variableStoreMapper.get(entityId) ?: return false
        val variable = variables[key] as? BitwiseVariable<T> ?: return false
        val operand = variable.getValue(id) ?: return false
        val value = store.get(variable)

        return value.has(operand)
    }

    @Subscribe
    private fun <T : Any> setVariable(action: SetVariable<T>) {
        set(action.entity, action.key, action.value, action.refresh)
    }

    @Subscribe
    private fun sendVariable(action: SendVariable) {
        send(action.entity, action.key)
    }

    @Subscribe
    private fun <T : Any> addVariable(action: AddVariable<T>) {
        add(action.entity, action.key, action.value, action.refresh)
    }

    @Subscribe
    private fun <T : Any> removeVariable(action: RemoveVariable<T>) {
        remove(action.entity, action.key, action.value, action.refresh)
    }

    @Subscribe
    private fun <T : Any> toggleVariable(action: ToggleVariable) {
        set(action.entity, action.key, !get(action.entity, action.key, false), action.refresh)
    }

    internal fun <T : Any> Variable<T>.send(entityId: Int, store: VariableStore) {
        val value = store.get(this)
        es.send(entityId, when (type) {
            Variable.Type.VARP -> Varp(id, toInt(value))
            Variable.Type.VARBIT -> Varbit(id, toInt(value))
            Variable.Type.VARC -> Varc(id, toInt(value))
            Variable.Type.VARCSTR -> VarcStr(id, value as String)
        })
    }

    companion object {
        private val logger = LoggerFactory.getLogger(VariableSystem::class.java)!!
        val names = mutableMapOf<String, Int>()
        val variables = mutableMapOf<Int, Variable<*>>()

        /**
         * Checks a [BitwiseVariable] for [id] value
         * @return pow(2, index) or null if not found
         */
        private fun <T : Any> BitwiseVariable<T>.getPower(id: T): Int? {
            val index = values.indexOf(id)
            if (index == -1) {
                return null//Invalid value
            }
            return 1 shl index//Return power of 2 of the index
        }

        /**
         * Checks if value [this] contains value [power]
         */
        private fun Int.has(power: Int) = (this and power) != 0

        /**
         * Gets [VariableStore]'s current value or [variable] default
         */
        private fun <T : Any> VariableStore.get(variable: Variable<T>): T {
            return values[variable.hash] as? T ?: variable.defaultValue
        }

        /**
         * Sets [VariableStore] value, removes if [variable] default
         * @return whether the value was changed
         */
        private fun <T : Any> VariableStore.set(variable: Variable<T>, value: T): Boolean {
            val changed: Boolean
            if (value == variable.defaultValue) {
                changed = values.containsKey(variable.hash)
                values.remove(variable.hash)
            } else {
                changed = values.getOrDefault(variable.hash, variable.defaultValue) != value
                values[variable.hash] = value
            }
            return changed
        }

        /**
         * Extension for [variables] to get using [names]
         */
        private operator fun <T : Any> Map<Int, T>.get(key: String): T? {
            return get(names[key])
        }
    }
}