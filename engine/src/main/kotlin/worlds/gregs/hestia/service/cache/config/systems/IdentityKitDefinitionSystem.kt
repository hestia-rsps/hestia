package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.IdentityKitDefinition
import world.gregs.hestia.cache.definition.config.readers.IdentityKitDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class IdentityKitDefinitionSystem : DefinitionSystem<IdentityKitDefinition>() {

    override val reader = IdentityKitDefinitionReader(CacheSystem.store)

}