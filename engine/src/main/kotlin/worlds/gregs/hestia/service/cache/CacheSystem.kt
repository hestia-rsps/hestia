package worlds.gregs.hestia.service.cache

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.cache.store.Index

class CacheSystem : PassiveSystem() {

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getIndex(id: Int): Index {
        return store.getIndex(id)
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getFile(index: Int, archive: Int): ByteArray? {
        return store.getFile(index, archive)
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getFile(index: Int, archive: Int, file: Int): ByteArray? {
        return store.getFile(index, archive, file)
    }

    companion object {
        val store = CacheStore()
    }

}