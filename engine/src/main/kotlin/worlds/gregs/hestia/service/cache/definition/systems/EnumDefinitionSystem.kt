package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.EnumDefinition
import worlds.gregs.hestia.service.cache.definition.readers.EnumDefinitionReader

class EnumDefinitionSystem(override val reader: DefinitionReader<EnumDefinition> = EnumDefinitionReader(CacheSystem.store)) : DefinitionSystem<EnumDefinition>()