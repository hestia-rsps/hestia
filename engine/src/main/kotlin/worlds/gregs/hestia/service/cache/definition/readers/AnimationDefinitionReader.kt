package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.AnimationDefinition
import java.util.concurrent.ConcurrentHashMap

class AnimationDefinitionReader(cacheStore: CacheStore) : DefinitionReader<AnimationDefinition> {

    override val index = cacheStore.getIndex(20)

    override val cache = ConcurrentHashMap<Int, AnimationDefinition>()

    override fun create(id: Int, member: Boolean) = AnimationDefinition().apply {
        this.id = id
        readData(id ushr 7, 0x7f and id)
        changeValues()
    }
}