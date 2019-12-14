package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.network.codec.packet.PacketReader
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.BodyDefinition
import java.util.concurrent.ConcurrentHashMap

class BodyDefinitionReader(cacheStore: CacheStore) : DefinitionReader<BodyDefinition> {

    override val index = cacheStore.getIndex(28)

    override val cache = ConcurrentHashMap<Int, BodyDefinition>()

    override fun create(id: Int, member: Boolean) = BodyDefinition().apply {
        val data = index.getFile(6)
        if (data != null) {
            readValueLoop(PacketReader(data), member)
        }
    }
}