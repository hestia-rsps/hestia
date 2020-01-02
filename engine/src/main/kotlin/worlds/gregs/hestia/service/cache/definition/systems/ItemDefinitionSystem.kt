package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.service.cache.definition.readers.ItemDefinitionReader

class ItemDefinitionSystem(override val reader: DefinitionReader<ItemDefinition> = ItemDefinitionReader(CacheSystem.store)) : DefinitionSystem<ItemDefinition>()