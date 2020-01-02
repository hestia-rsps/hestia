package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.PlayerVariableParameterDefinition
import worlds.gregs.hestia.service.cache.config.readers.PlayerVariableParameterDefinitionReader

class PlayerVariableParameterDefinitionSystem : DefinitionSystem<PlayerVariableParameterDefinition>() {

    override val reader = PlayerVariableParameterDefinitionReader(CacheSystem.store)

}