package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.ObjectDefinition
import worlds.gregs.hestia.service.cache.definition.readers.ObjectDefinitionReader

class ObjectDefinitionSystem(override val reader: DefinitionReader<ObjectDefinition> = ObjectDefinitionReader(CacheSystem.store)) : DefinitionSystem<ObjectDefinition>()