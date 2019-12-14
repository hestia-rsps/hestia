package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.WidgetDefinition
import worlds.gregs.hestia.service.cache.definition.definitions.WindowDefinition
import java.util.concurrent.ConcurrentHashMap

class WindowDefinitionReader(cacheStore: CacheStore) : DefinitionReader<WindowDefinition> {

    override val index = cacheStore.getIndex(3)

    override val cache = ConcurrentHashMap<Int, WindowDefinition>()

    override fun create(id: Int, member: Boolean): WindowDefinition {
        val size = index.getLastFileId(id) + 1
        return WindowDefinition(size).apply {
            repeat(size) { index ->
                add(index, WidgetDefinition().apply {
                    this.id = index + (id shl 16)
                    readData(id ushr 8, id and 0xff)
                })
            }
        }
    }
}