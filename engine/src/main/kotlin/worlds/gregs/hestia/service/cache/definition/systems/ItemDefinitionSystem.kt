package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import world.gregs.hestia.cache.definition.readers.ItemDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class ItemDefinitionSystem(override val reader: DefinitionReader<ItemDefinition> = ItemDefinitionReader(CacheSystem.store)) : DefinitionSystem<ItemDefinition>() {

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