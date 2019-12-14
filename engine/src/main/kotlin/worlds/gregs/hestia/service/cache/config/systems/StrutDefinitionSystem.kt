package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.StrutDefinition
import worlds.gregs.hestia.service.cache.config.readers.StrutDefinitionReader

class StrutDefinitionSystem : DefinitionSystem<StrutDefinition>() {

    override val reader = StrutDefinitionReader(CacheSystem.store)

}