package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.WindowDefinition
import worlds.gregs.hestia.service.cache.definition.readers.WindowDefinitionReader

class WindowDefinitionSystem(override val reader: DefinitionReader<WindowDefinition> = WindowDefinitionReader(CacheSystem.store)) : DefinitionSystem<WindowDefinition>()