package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.ItemContainerDefinition
import world.gregs.hestia.cache.definition.config.readers.ItemContainerDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class ItemContainerDefinitionSystem : DefinitionSystem<ItemContainerDefinition>() {

    override val reader = ItemContainerDefinitionReader(CacheSystem.store)

}