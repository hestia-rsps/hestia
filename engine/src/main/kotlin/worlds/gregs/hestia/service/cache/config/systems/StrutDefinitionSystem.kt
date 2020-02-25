package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.StrutDefinition
import world.gregs.hestia.cache.definition.config.readers.StrutDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class StrutDefinitionSystem : DefinitionSystem<StrutDefinition>() {

    override val reader = StrutDefinitionReader(CacheSystem.store)

}