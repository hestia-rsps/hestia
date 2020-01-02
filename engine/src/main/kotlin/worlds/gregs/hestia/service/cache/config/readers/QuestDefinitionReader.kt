package worlds.gregs.hestia.service.cache.config.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.ConfigReader
import worlds.gregs.hestia.service.cache.config.definitions.QuestDefinition
import java.util.concurrent.ConcurrentHashMap

class QuestDefinitionReader(cacheStore: CacheStore) : ConfigReader<QuestDefinition>(cacheStore) {

    override val archive = 35

    override val cache = ConcurrentHashMap<Int, QuestDefinition>()

    override fun create(id: Int, member: Boolean) = QuestDefinition().apply {
        readData(archive, id)
        changeValues()
    }
}