package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.InterfaceDefinition
import worlds.gregs.hestia.service.cache.definition.readers.InterfaceDefinitionReader

class InterfaceDefinitionSystem(override val reader: DefinitionReader<InterfaceDefinition> = InterfaceDefinitionReader(CacheSystem.store)) : DefinitionSystem<InterfaceDefinition>()