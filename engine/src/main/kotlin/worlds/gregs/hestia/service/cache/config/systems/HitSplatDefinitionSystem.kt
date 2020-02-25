package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.HitSplatDefinition
import world.gregs.hestia.cache.definition.config.readers.HitSplatDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class HitSplatDefinitionSystem : DefinitionSystem<HitSplatDefinition>() {

    override val reader = HitSplatDefinitionReader(CacheSystem.store)

}