package worlds.gregs.hestia.game.plugins.core.systems.cache

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.cache.store.Index

class CacheSystem : PassiveSystem() {

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getIndex(id: Int): Index {
        return cache.getIndex(id)
    }

    companion object {
        private val cache = CacheStore()
    }

}