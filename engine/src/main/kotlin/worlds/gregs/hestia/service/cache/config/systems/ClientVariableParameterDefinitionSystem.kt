package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.ClientVariableParameterDefinition
import worlds.gregs.hestia.service.cache.config.readers.ClientVariableParameterDefinitionReader

class ClientVariableParameterDefinitionSystem : DefinitionSystem<ClientVariableParameterDefinition>() {

    override val reader = ClientVariableParameterDefinitionReader(CacheSystem.store)

}