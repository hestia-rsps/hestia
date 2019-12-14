package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.MobDefinition
import java.util.concurrent.ConcurrentHashMap

class MobDefinitionReader(cacheStore: CacheStore) : DefinitionReader<MobDefinition> {

    override val index = cacheStore.getIndex(18)

    override val cache = ConcurrentHashMap<Int, MobDefinition>()

    override fun create(id: Int, member: Boolean) = MobDefinition().apply {
        this.id = id
        this.options = defaultOptions.clone()

        readData(id ushr 7, 0x7f and id, member)

        changeValues()
    }

    companion object {
        private val defaultOptions = arrayOf(null, null, null, null, null, "Examine")
    }
}