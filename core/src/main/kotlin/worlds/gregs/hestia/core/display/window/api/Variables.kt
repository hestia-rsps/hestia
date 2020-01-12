package worlds.gregs.hestia.core.display.window.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.window.model.components.VariableStore
import worlds.gregs.hestia.core.display.window.model.variable.BitwiseVariable

abstract class Variables : SubscriptionSystem(Aspect.all(VariableStore::class)) {

    /**
     * Sets and sends a variable value for [entityId]
     */
    abstract fun <T> set(entityId: Int, key: String, value: T)

    /**
     * Sends a variables current value for [entityId]
     */
    abstract fun send(entityId: Int, key: String)

    /**
     * Returns the variable value for [entityId]
     */
    abstract fun <T> get(entityId: Int, key: String, default: T): T

    /**
     * Adds [id] value to [BitwiseVariable] [key]
     */
    abstract fun <T> add(entityId: Int, key: String, id: T)

    /**
     * Removes [id] value from [BitwiseVariable] [key]
     */
    abstract fun <T> remove(entityId: Int, key: String, id: T)

    /**
     * Checks [BitwiseVariable] [key] for [id] value
     */
    abstract fun <T> has(entityId: Int, key: String, id: T): Boolean

}