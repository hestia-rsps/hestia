package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.WorldMapInfoDefinition
import worlds.gregs.hestia.service.cache.config.readers.WorldMapInfoDefinitionReader

class WorldMapInfoDefinitionSystem : DefinitionSystem<WorldMapInfoDefinition>() {

    override val reader = WorldMapInfoDefinitionReader(CacheSystem.store)

}