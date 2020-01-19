package worlds.gregs.hestia.core.entity.entity.model.components

import com.artemis.Component

class Blackboard : Component() {
    private val map = HashMap<String, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defaultValue: T): T {
        return (map[key] as? T) ?: defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getUnsafe(key: String): T? {
        return (map[key] as? T)
    }

    fun getBoolean(key: String) = get(key, false)

    fun getLong(key: String) = get(key, -1L)

    fun getInt(key: String) = get(key, -1)

    fun getDouble(key: String) = get(key, -1.0)

    fun getString(key: String) = get(key, "")

    operator fun set(key: String, value: Any) {
        map[key] = value
    }

    fun remove(key: String) = map.remove(key)
}