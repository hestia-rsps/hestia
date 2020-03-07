package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.ObjectDefinition
import world.gregs.hestia.cache.definition.readers.ObjectDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class ObjectDefinitionSystem(override val reader: DefinitionReader<ObjectDefinition> = ObjectDefinitionReader(CacheSystem.store)) : DefinitionSystem<ObjectDefinition>() {

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