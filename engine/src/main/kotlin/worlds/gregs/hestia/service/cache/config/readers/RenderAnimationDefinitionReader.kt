package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.RenderAnimationDefinition
import java.util.concurrent.ConcurrentHashMap

class RenderAnimationDefinitionReader(cacheStore: CacheStore) : ConfigReader<RenderAnimationDefinition>(cacheStore) {

    override val archive: Int = 32

    override val cache = ConcurrentHashMap<Int, RenderAnimationDefinition>()

    override fun create(id: Int, member: Boolean) = RenderAnimationDefinition().apply {
        readData(archive, id)
    }
}