package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.HitSplatDefinition
import worlds.gregs.hestia.service.cache.config.readers.HitSplatDefinitionReader

class HitSplatDefinitionSystem : DefinitionSystem<HitSplatDefinition>() {

    override val reader = HitSplatDefinitionReader(CacheSystem.store)

}