package worlds.gregs.hestia.services

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.Cache
import worlds.gregs.hestia.services.definitions.ObjectDefinition
import java.util.concurrent.ConcurrentHashMap

object ObjectDefinitions {
    private val cache = ConcurrentHashMap<Int, ObjectDefinition>()

    val objectDefinitionsSize: Int
        get() {
            val lastArchiveId = Cache.getIndex(16)?.lastArchiveId ?: 0
            return lastArchiveId * 256 + (Cache.getIndex(16)?.getValidFilesCount(lastArchiveId) ?: 0)
        }

    private fun getArchiveId(id: Int): Int {
        return id.ushr(-1135990488)
    }

    fun get(id: Int): ObjectDefinition {
        var definition = cache[id]

        if (definition == null) {
            definition = ObjectDefinition()
            definition.id = id

            val data = Cache.getIndex(16)?.getFile(getArchiveId(id), id and 0xff)
            if (data != null) {
                definition.readValueLoop(Packet(data))
            }

            definition.apply {
                changeValues()
                if(name.equals("bank booth", true) || name.equals("counter", true)) {
                    ignoreClipOnAlternativeRoute = false
                    isProjectileClipped = true
                    if(solid == 0) {
                        solid = 1
                    }
                }
                if(ignoreClipOnAlternativeRoute) {
                    isProjectileClipped = false
                    solid = 0
                }
            }
            cache[id] = definition
        }
        return definition
    }

    fun clearObjectDefinitions() {
        cache.clear()
    }
}