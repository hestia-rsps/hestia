package worlds.gregs.hestia.core.display.window.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Variables
import worlds.gregs.hestia.core.display.window.model.components.VariableStore
import worlds.gregs.hestia.core.display.window.model.events.*
import worlds.gregs.hestia.core.display.window.model.variable.BitwiseVariable
import worlds.gregs.hestia.network.client.encoders.messages.Varbit
import worlds.gregs.hestia.network.client.encoders.messages.Varc
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
            if(variable.persistent) {
                println("Save ${names.entries.firstOrNull { it.value == hash }} $value")
            }
        }
    }

    override fun <T> set(entityId: Int, key: String, value: T) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] as? Variable<T> ?: return logger.warn("Cannot variable for key '$key'")
        val intValue = variable.getInt(value)
        store.set(variable, intValue)
        variable.send(entityId, store)
    }

    override fun send(entityId: Int, key: String) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] ?: return logger.warn("Cannot variable for key '$key'")

        variable.send(entityId, store)
    }

    override fun <T> get(entityId: Int, key: String, default: T): T {
        val store = variableStoreMapper.get(entityId) ?: return default
        val variable = variables[key] as? Variable<T> ?: return default
        return variable.getValue(store.get(variable))
    }

    override fun <T> add(entityId: Int, key: String, id: T) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] as? BitwiseVariable<T> ?: return logger.warn("Cannot variable for key '$key'")

        val power = variable.getPower(id) ?: return logger.warn("Invalid bitwise value '$id'")
        val value = store.get(variable)

        if(!value.has(power)) {//If isn't already added
            store.set(variable, value + power)//Add
            variable.send(entityId, store)
        }
    }

    override fun <T> remove(entityId: Int, key: String, id: T) {
        val store = variableStoreMapper.get(entityId) ?: return
        val variable = variables[key] as? BitwiseVariable<T> ?: return logger.warn("Cannot variable for key '$key'")

        val power = variable.getPower(id) ?: return logger.warn("Invalid bitwise value '$id'")
        val value = store.get(variable)

        if(value.has(power)) {//If is added
            store.set(variable, value - power)//Remove
            variable.send(entityId, store)
        }
    }

    override fun <T> has(entityId: Int, key: String, id: T): Boolean {
        val store = variableStoreMapper.get(entityId) ?: return false
        val variable = variables[key] as? BitwiseVariable<T> ?: return false

        val power = variable.getPower(id) ?: return false
        val value = store.get(variable)

        return value.has(power)
    }

    @Subscribe
    private fun <T> setVariable(action: SetVariable<T>) {
        set(action.entity, action.key, action.value)
    }

    @Subscribe
    private fun sendVariable(action: SendVariable) {
        send(action.entity, action.key)
    }

    @Subscribe
    private fun <T> addVariable(action: AddVariable<T>) {
        add(action.entity, action.key, action.value)
    }

    @Subscribe
    private fun <T> removeVariable(action: RemoveVariable<T>) {
        remove(action.entity, action.key, action.value)
    }

    @Subscribe
    private fun <T> toggleVariable(action: ToggleVariable) {
        set(action.entity, action.key, !get(action.entity, action.key, false))
    }

    private fun <T> Variable<T>.send(entityId: Int, store: VariableStore) {
        val value = store.get(this)
        es.send(entityId, when(type) {
            Variable.Type.VARP -> Varp(id, value)//Config
            Variable.Type.VARBIT -> Varbit(id, value)//File
            Variable.Type.VARC -> Varc(id, value)//Global
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
        private fun <T> BitwiseVariable<T>.getPower(id: T) : Int? {
            val index = values.indexOf(id)
            if(index == -1) {
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
        private fun VariableStore.get(variable: Variable<*>): Int {
            return values[variable.hash] ?: variable.defaultValue
        }

        /**
         * Sets [VariableStore] value, removes if [variable] default
         */
        private fun <T> VariableStore.set(variable: Variable<T>, value: Int) {
            if(value == variable.defaultValue) {
                values.remove(variable.hash)
            } else {
                values[variable.hash] = value
            }
        }

        /**
         * Extension for [variables] to get using [names]
         */
        private operator fun <T> Map<Int, T>.get(key: String): T? {
            return get(names[key])
        }
    }
}