package worlds.gregs.hestia.service.cache

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.Settings

class CacheSystem : PassiveSystem() {

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getIndex(id: Int): Index {
        return store.index(id)
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getFile(index: Int, archive: Int): ByteArray? {
        return store.index(index).archive(archive)?.file(0)?.data
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getFile(index: Int, archive: Int, file: Int): ByteArray? {
        return store.index(index).archive(archive)?.file(file)?.data
    }

    companion object {
        val store = CacheStore()
        val store718 = CacheStore("${System.getProperty("user.home")}\\Downloads\\rs718_cache\\")
        val store = CacheLibrary(Settings.getString("cachePath")!!)
    }

}