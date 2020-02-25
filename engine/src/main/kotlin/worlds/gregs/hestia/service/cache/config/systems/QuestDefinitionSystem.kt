package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.QuestDefinition
import world.gregs.hestia.cache.definition.config.readers.QuestDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class QuestDefinitionSystem : DefinitionSystem<QuestDefinition>() {

    override val reader = QuestDefinitionReader(CacheSystem.store)

}