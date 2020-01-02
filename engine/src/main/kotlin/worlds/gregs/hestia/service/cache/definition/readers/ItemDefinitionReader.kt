package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition
import java.util.concurrent.ConcurrentHashMap

class ItemDefinitionReader(cacheStore: CacheStore) : DefinitionReader<ItemDefinition> {

    override val index = cacheStore.getIndex(19)

    override val cache = ConcurrentHashMap<Int, ItemDefinition>()

    override fun create(id: Int, member: Boolean) = ItemDefinition().apply {
        this.id = id
        this.floorOptions = defaultGroundOptions.clone()
        this.options = defaultOptions.clone()

        readData(id ushr 8, id and 0xff)

        if (notedTemplateId != -1) {
            toNote(get(notedTemplateId), get(noteId))
        }
        if (lendTemplateId != -1) {
            toLend(get(lendId), get(lendTemplateId))
        }
        if (bindTemplateId != -1) {
            toBind(get(bindTemplateId), get(bindId))
        }
        if (!member && members) {
            name = "Members object"
            floorOptions = defaultGroundOptions
            options = defaultOptions
            campaigns = null
            team = 0
            unnoted = false
            params = null
        }
    }

    companion object {
        val defaultOptions = arrayOf(null, null, null, null, "Drop")
        private val defaultGroundOptions = arrayOf(null, null, "Take", null, null, "Examine")
    }
}