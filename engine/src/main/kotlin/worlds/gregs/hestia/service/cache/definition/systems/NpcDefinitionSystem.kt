package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.NpcDefinition
import world.gregs.hestia.cache.definition.readers.NpcDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class NpcDefinitionSystem(override val reader: DefinitionReader<NpcDefinition> = NpcDefinitionReader(CacheSystem.store)) : DefinitionSystem<NpcDefinition>() {

    val names = mutableListOf<Pair<String, Int>>()
    init {
        repeat(reader.size) { id ->
            val def = reader.get(id)
            if(def.name.isNotEmpty() && def.name != "null") {
                names.add(def.name to id)
            }
        }
    }
}