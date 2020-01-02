package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.VarBitDefinition
import java.util.concurrent.ConcurrentHashMap

class VarBitDefinitionReader(cacheStore: CacheStore) : DefinitionReader<VarBitDefinition> {

    override val index = cacheStore.getIndex(22)

    override val cache = ConcurrentHashMap<Int, VarBitDefinition>()

    override fun create(id: Int, member: Boolean) = VarBitDefinition().apply {
        readData(id ushr 10, 0x3ff and id)
    }
}