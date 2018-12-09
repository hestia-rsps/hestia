package worlds.gregs.hestia.game.plugins.core.systems.cache

import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.services.definitions.ObjectDefinition
import java.util.concurrent.ConcurrentHashMap

class ObjectDefinitionSystem : PassiveSystem() {

    private lateinit var cacheSystem: CacheSystem
    private val cache = ConcurrentHashMap<Int, ObjectDefinition>()

    val size: Int
        get() {
            val lastArchiveId = cacheSystem.getIndex(16)!!.lastArchiveId
            return lastArchiveId * 256 + (cacheSystem.getIndex(16)!!.getValidFilesCount(lastArchiveId))
        }

    private fun getArchiveId(id: Int): Int {
        return id.ushr(-1135990488)
    }

    fun get(id: Int): ObjectDefinition {
        var definition = cache[id]

        if (definition == null) {
            definition = ObjectDefinition()
            definition.id = id

            val data = cacheSystem.getIndex(16)!!.getFile(getArchiveId(id), id and 0xff)
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