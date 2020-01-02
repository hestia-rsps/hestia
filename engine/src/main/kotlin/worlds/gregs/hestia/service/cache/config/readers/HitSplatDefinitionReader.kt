package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.HitSplatDefinition
import java.util.concurrent.ConcurrentHashMap

class HitSplatDefinitionReader(cacheStore: CacheStore) : ConfigReader<HitSplatDefinition>(cacheStore) {

    override val archive = 46

    override val cache = ConcurrentHashMap<Int, HitSplatDefinition>()

    override fun create(id: Int, member: Boolean) = HitSplatDefinition().apply {
        readData(archive, id)
    }
}