package worlds.gregs.hestia.service.cache

import world.gregs.hestia.core.cache.store.Index
import world.gregs.hestia.core.network.codec.packet.PacketReader
import java.util.concurrent.ConcurrentHashMap

interface DefinitionReader<T : Definition> {

    val index: Index

    val cache: ConcurrentHashMap<Int, T>

    open val size: Int
        get() {
            val lastArchiveId = index.lastArchiveId
            return lastArchiveId * 256 + (index.getValidFilesCount(lastArchiveId))
        }

    fun get(id: Int, member: Boolean = true): T = cache.getOrPut(id) { create(id, member) }

    fun create(id: Int, member: Boolean): T

    fun T.readData(archive: Int, id: Int, member: Boolean = true) {
        val data = index.getFile(archive, id)
        if (data != null) {
            readValueLoop(PacketReader(data), member)
        }
    }

    fun clear() {
        cache.clear()
    }
}