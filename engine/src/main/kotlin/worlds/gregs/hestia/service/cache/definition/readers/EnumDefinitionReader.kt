package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.EnumDefinition
import java.util.concurrent.ConcurrentHashMap

class EnumDefinitionReader(cacheStore: CacheStore) : DefinitionReader<EnumDefinition> {

    override val index = cacheStore.getIndex(17)

    override val cache = ConcurrentHashMap<Int, EnumDefinition>()

    override fun create(id: Int, member: Boolean) = EnumDefinition().apply {
        readData(id ushr 8, id and 0xff)
    }
}