package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.PlayerVariableParameterDefinition
import world.gregs.hestia.cache.definition.config.readers.PlayerVariableParameterDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class PlayerVariableParameterDefinitionSystem : DefinitionSystem<PlayerVariableParameterDefinition>() {

    override val reader = PlayerVariableParameterDefinitionReader(CacheSystem.store)

}