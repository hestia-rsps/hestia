package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.PlayerVariableParameterDefinition
import java.util.concurrent.ConcurrentHashMap

class PlayerVariableParameterDefinitionReader(cacheStore: CacheStore) : ConfigReader<PlayerVariableParameterDefinition>(cacheStore) {

    override val archive: Int = 16

    override val cache = ConcurrentHashMap<Int, PlayerVariableParameterDefinition>()

    override fun create(id: Int, member: Boolean) = PlayerVariableParameterDefinition().apply {
        readData(archive, id)
    }
}