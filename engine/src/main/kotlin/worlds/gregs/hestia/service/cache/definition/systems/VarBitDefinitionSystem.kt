package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.VarBitDefinition
import worlds.gregs.hestia.service.cache.definition.readers.VarBitDefinitionReader

class VarBitDefinitionSystem(override val reader: DefinitionReader<VarBitDefinition> = VarBitDefinitionReader(CacheSystem.store)) : DefinitionSystem<VarBitDefinition>()