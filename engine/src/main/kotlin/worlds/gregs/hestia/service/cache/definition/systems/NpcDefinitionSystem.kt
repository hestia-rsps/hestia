package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.NpcDefinition
import worlds.gregs.hestia.service.cache.definition.readers.NpcDefinitionReader

class NpcDefinitionSystem(override val reader: DefinitionReader<NpcDefinition> = NpcDefinitionReader(CacheSystem.store)) : DefinitionSystem<NpcDefinition>()