package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.WorldMapInfoDefinition
import world.gregs.hestia.cache.definition.config.readers.WorldMapInfoDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class WorldMapInfoDefinitionSystem : DefinitionSystem<WorldMapInfoDefinition>() {

    override val reader = WorldMapInfoDefinitionReader(CacheSystem.store)

}