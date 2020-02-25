package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.WorldMapDefinition
import world.gregs.hestia.cache.definition.readers.WorldMapDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class WorldMapDefinitionSystem(override val reader: DefinitionReader<WorldMapDefinition> = WorldMapDefinitionReader(CacheSystem.store)) : DefinitionSystem<WorldMapDefinition>()