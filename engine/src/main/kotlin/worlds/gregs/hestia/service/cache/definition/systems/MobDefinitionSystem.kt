package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.MobDefinition
import worlds.gregs.hestia.service.cache.definition.readers.MobDefinitionReader

class MobDefinitionSystem(override val reader: DefinitionReader<MobDefinition> = MobDefinitionReader(CacheSystem.store)) : DefinitionSystem<MobDefinition>()