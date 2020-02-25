package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.InterfaceDefinition
import world.gregs.hestia.cache.definition.readers.InterfaceDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class InterfaceDefinitionSystem(override val reader: DefinitionReader<InterfaceDefinition> = InterfaceDefinitionReader(CacheSystem.store)) : DefinitionSystem<InterfaceDefinition>()