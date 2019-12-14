package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.ClientVariableParameterDefinition
import java.util.concurrent.ConcurrentHashMap

class ClientVariableParameterDefinitionReader(cacheStore: CacheStore) : ConfigReader<ClientVariableParameterDefinition>(cacheStore) {

    override val archive: Int = 19

    override val cache = ConcurrentHashMap<Int, ClientVariableParameterDefinition>()

    override fun create(id: Int, member: Boolean) = ClientVariableParameterDefinition().apply {
        readData(archive, id)
    }
}