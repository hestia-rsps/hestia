package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.IdentityKitDefinition
import worlds.gregs.hestia.service.cache.config.readers.IdentityKitDefinitionReader

class IdentityKitDefinitionSystem : DefinitionSystem<IdentityKitDefinition>() {

    override val reader = IdentityKitDefinitionReader(CacheSystem.store)

}