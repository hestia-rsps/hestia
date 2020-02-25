package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.GraphicDefinition
import world.gregs.hestia.cache.definition.readers.GraphicDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class GraphicDefinitionSystem(override val reader: DefinitionReader<GraphicDefinition> = GraphicDefinitionReader(CacheSystem.store)) : DefinitionSystem<GraphicDefinition>()