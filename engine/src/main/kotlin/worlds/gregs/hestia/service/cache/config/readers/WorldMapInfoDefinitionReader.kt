package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.WorldMapInfoDefinition
import java.util.concurrent.ConcurrentHashMap

class WorldMapInfoDefinitionReader(cacheStore: CacheStore) : ConfigReader<WorldMapInfoDefinition>(cacheStore) {

    override val archive = 36

    override val cache = ConcurrentHashMap<Int, WorldMapInfoDefinition>()

    override fun create(id: Int, member: Boolean) = WorldMapInfoDefinition().apply {
        this.id = id
        readData(archive, id)
        changeValues()
    }
}