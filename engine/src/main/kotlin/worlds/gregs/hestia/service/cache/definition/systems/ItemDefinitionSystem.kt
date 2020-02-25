package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import world.gregs.hestia.cache.definition.readers.ItemDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class ItemDefinitionSystem(override val reader: DefinitionReader<ItemDefinition> = ItemDefinitionReader(CacheSystem.store)) : DefinitionSystem<ItemDefinition>()