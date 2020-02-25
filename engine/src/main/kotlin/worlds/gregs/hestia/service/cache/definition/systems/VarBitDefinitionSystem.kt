package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.VarBitDefinition
import world.gregs.hestia.cache.definition.readers.VarBitDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class VarBitDefinitionSystem(override val reader: DefinitionReader<VarBitDefinition> = VarBitDefinitionReader(CacheSystem.store)) : DefinitionSystem<VarBitDefinition>()