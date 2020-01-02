package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.WorldMapDefinition
import worlds.gregs.hestia.service.cache.definition.readers.WorldMapDefinitionReader

class WorldMapDefinitionSystem(override val reader: DefinitionReader<WorldMapDefinition> = WorldMapDefinitionReader(CacheSystem.store)) : DefinitionSystem<WorldMapDefinition>()