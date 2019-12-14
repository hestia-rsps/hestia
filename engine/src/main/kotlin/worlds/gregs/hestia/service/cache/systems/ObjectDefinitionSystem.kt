package worlds.gregs.hestia.service.cache.systems

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.codec.packet.PacketReader
import worlds.gregs.hestia.service.cache.definitions.ObjectDefinition
import java.util.concurrent.ConcurrentHashMap

class ObjectDefinitionSystem : PassiveSystem() {

    private lateinit var cacheSystem: CacheSystem
    private val cache = ConcurrentHashMap<Int, ObjectDefinition>()

    val size: Int
        get() {
            val lastArchiveId = cacheSystem.getIndex(16).lastArchiveId
            return lastArchiveId * 256 + (cacheSystem.getIndex(16).getValidFilesCount(lastArchiveId))
        }

    fun get(id: Int): ObjectDefinition {
        var definition = cache[id]

        if (definition == null) {
            definition = ObjectDefinition()
            definition.id = id

            val data = cacheSystem.getFile(16, id.ushr(8), id and 0xff)
            if (data != null) {
                definition.readValueLoop(PacketReader(data))
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