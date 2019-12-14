package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.GraphicDefinition
import worlds.gregs.hestia.service.cache.definition.readers.GraphicDefinitionReader

class GraphicDefinitionSystem(override val reader: DefinitionReader<GraphicDefinition> = GraphicDefinitionReader(CacheSystem.store)) : DefinitionSystem<GraphicDefinition>()