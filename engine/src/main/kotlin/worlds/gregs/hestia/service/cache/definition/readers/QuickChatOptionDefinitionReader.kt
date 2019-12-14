package worlds.gregs.hestia.service.cache.definition.readers

import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.network.codec.packet.PacketReader
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.definition.definitions.QuickChatOptionDefinition
import java.util.concurrent.ConcurrentHashMap

class QuickChatOptionDefinitionReader(cacheStore: CacheStore) : DefinitionReader<QuickChatOptionDefinition> {

    override val index = cacheStore.getIndex(24)

    val secondIndex = cacheStore.getIndex(25)

    override val size: Int
        get() = (index.lastArchiveId * 256 + (index.getValidFilesCount(index.lastArchiveId))) + (secondIndex.lastArchiveId * 256 + (secondIndex.getValidFilesCount(secondIndex.lastArchiveId)))

    override val cache = ConcurrentHashMap<Int, QuickChatOptionDefinition>()

    override fun create(id: Int, member: Boolean) = QuickChatOptionDefinition().apply {
        val data = if(id < 32768) {
            index.getFile(0, id)
        } else {
            secondIndex.getFile(0, id and 0x7fff)
        }

        if(data != null) {
            readValueLoop(PacketReader(data), member)
        }

        if(id >= 32768) {
            changeValues()
        }
    }
}