package worlds.gregs.hestia.core.display.variable.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.variable.model.VariableStore
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVariable

abstract class Variables : SubscriptionSystem(Aspect.all(VariableStore::class)) {

    /**
     * Sets and sends a variable value for [entityId]
     * @param entityId The entity id who's variable to change
     * @param key The variable name
     * @param value The value to set the variable to
     * @param refresh Whether to send a client update or not
     */
    abstract fun <T : Any> set(entityId: Int, key: String, value: T, refresh: Boolean = true)

    /**
     * Sends a variables current value to the client
     * @param entityId The entity id who's variable to send
     * @param key The variable name
     */
    abstract fun send(entityId: Int, key: String)

    /**
     * Returns the variable value for [entityId]
     * @param entityId The entity id who's variable to get
     * @param key The variable name
     * @param default The default value to return if not found
     */
    abstract fun <T : Any> get(entityId: Int, key: String, default: T): T

    /**
     * Adds [id] value to [BitwiseVariable] [key]
     * @param entityId The entity id who's bitwise variable to change
     * @param key The variable name
     * @param id The value to add
     * @param refresh Whether to send a client update or not
     */
    abstract fun <T : Any> add(entityId: Int, key: String, id: T, refresh: Boolean = true)

    /**
     * Removes [id] value from [BitwiseVariable] [key]
     * @param entityId The entity id who's bitwise variable to change
     * @param key The variable name
     * @param id The value to remove
     * @param refresh Whether to send a client update or not
     */
    abstract fun <T : Any> remove(entityId: Int, key: String, id: T, refresh: Boolean = true)

    /**
     * Checks [BitwiseVariable] [key] for [id] value
     * @param entityId The entity id who's bitwise variable to change
     * @param key The variable name
     * @param id The value to check for
     */
    abstract fun <T : Any> has(entityId: Int, key: String, id: T): Boolean

}