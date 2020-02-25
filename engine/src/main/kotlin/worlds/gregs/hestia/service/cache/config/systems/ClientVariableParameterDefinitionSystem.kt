package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.ClientVariableParameterDefinition
import world.gregs.hestia.cache.definition.config.readers.ClientVariableParameterDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class ClientVariableParameterDefinitionSystem : DefinitionSystem<ClientVariableParameterDefinition>() {

    override val reader = ClientVariableParameterDefinitionReader(CacheSystem.store)

}