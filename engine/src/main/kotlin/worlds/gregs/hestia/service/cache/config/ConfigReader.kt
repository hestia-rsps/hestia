package worlds.gregs.hestia.service.cache.config

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.Definition
import worlds.gregs.hestia.service.cache.DefinitionReader

abstract class ConfigReader<T : Definition>(cacheStore: CacheStore) : DefinitionReader<T> {

    override val index = cacheStore.getIndex(2)

    override val size: Int
        get() = index.getLastFileId(archive)

    abstract val archive: Int

}