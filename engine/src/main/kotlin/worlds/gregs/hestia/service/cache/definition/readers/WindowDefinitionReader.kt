package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.network.codec.packet.PacketReader
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.WidgetDefinition
import worlds.gregs.hestia.service.cache.definition.definitions.WindowDefinition
import java.util.concurrent.ConcurrentHashMap

class WindowDefinitionReader(cacheStore: CacheStore) : DefinitionReader<WindowDefinition> {

    override val index = cacheStore.getIndex(3)

    override val cache = ConcurrentHashMap<Int, WindowDefinition>()

    override fun create(id: Int, member: Boolean): WindowDefinition {
        val size = index.getLastFileId(id) + 1
        val widgets = (0 until size).map { i ->
            Pair(i, WidgetDefinition().apply {
                this.id = i + (id shl 16)
                val data = index.getFile(id, i)
                if (data != null) {
                    readValueLoop(PacketReader(data), member)
                }
            })
        }
        return WindowDefinition(widgets.toTypedArray())
    }
}