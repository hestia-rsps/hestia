package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.BodyDefinition
import worlds.gregs.hestia.service.cache.definition.readers.BodyDefinitionReader

class BodyDefinitionSystem(override val reader: DefinitionReader<BodyDefinition> = BodyDefinitionReader(CacheSystem.store)) : DefinitionSystem<BodyDefinition>()