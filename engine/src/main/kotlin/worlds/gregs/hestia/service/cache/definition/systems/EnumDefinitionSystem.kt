package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.EnumDefinition
import world.gregs.hestia.cache.definition.readers.EnumDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class EnumDefinitionSystem(override val reader: DefinitionReader<EnumDefinition> = EnumDefinitionReader(CacheSystem.store)) : DefinitionSystem<EnumDefinition>()