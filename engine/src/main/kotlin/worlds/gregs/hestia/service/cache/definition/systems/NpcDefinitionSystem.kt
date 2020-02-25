package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.NpcDefinition
import world.gregs.hestia.cache.definition.readers.NpcDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class NpcDefinitionSystem(override val reader: DefinitionReader<NpcDefinition> = NpcDefinitionReader(CacheSystem.store)) : DefinitionSystem<NpcDefinition>()