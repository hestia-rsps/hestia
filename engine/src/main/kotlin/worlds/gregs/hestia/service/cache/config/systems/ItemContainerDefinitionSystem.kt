package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.ItemContainerDefinition
import worlds.gregs.hestia.service.cache.config.readers.ItemContainerDefinitionReader

class ItemContainerDefinitionSystem : DefinitionSystem<ItemContainerDefinition>() {

    override val reader = ItemContainerDefinitionReader(CacheSystem.store)

}