package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.StrutDefinition
import java.util.concurrent.ConcurrentHashMap

class StrutDefinitionReader(cacheStore: CacheStore) : ConfigReader<StrutDefinition>(cacheStore) {

    override val archive = 26

    override val cache = ConcurrentHashMap<Int, StrutDefinition>()

    override fun create(id: Int, member: Boolean) = StrutDefinition().apply {
        readData(archive, id)
    }
}