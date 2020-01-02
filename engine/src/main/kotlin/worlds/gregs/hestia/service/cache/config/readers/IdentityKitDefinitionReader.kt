package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.IdentityKitDefinition
import java.util.concurrent.ConcurrentHashMap

class IdentityKitDefinitionReader(cacheStore: CacheStore) : ConfigReader<IdentityKitDefinition>(cacheStore) {

    override val archive = 3

    override val cache = ConcurrentHashMap<Int, IdentityKitDefinition>()

    override fun create(id: Int, member: Boolean) = IdentityKitDefinition().apply {
        readData(archive, id)
    }
}