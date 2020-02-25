package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.BodyDefinition
import world.gregs.hestia.cache.definition.readers.BodyDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class BodyDefinitionSystem(override val reader: DefinitionReader<BodyDefinition> = BodyDefinitionReader(CacheSystem.store)) : DefinitionSystem<BodyDefinition>()