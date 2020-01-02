package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.ItemContainerDefinition
import java.util.concurrent.ConcurrentHashMap

class ItemContainerDefinitionReader(cacheStore: CacheStore) : ConfigReader<ItemContainerDefinition>(cacheStore) {

    override val archive: Int = 5

    override val cache = ConcurrentHashMap<Int, ItemContainerDefinition>()

    override fun create(id: Int, member: Boolean) = ItemContainerDefinition().apply {
        readData(archive, id)
    }
}