package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.GraphicDefinition
import java.util.concurrent.ConcurrentHashMap

class GraphicDefinitionReader(cacheStore: CacheStore) : DefinitionReader<GraphicDefinition> {

    override val index = cacheStore.getIndex(21)

    override val cache = ConcurrentHashMap<Int, GraphicDefinition>()

    override fun create(id: Int, member: Boolean) = GraphicDefinition().apply {
        this.id = id

        readData(id ushr 8, id and 0xff)
    }
}