package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.QuestDefinition
import worlds.gregs.hestia.service.cache.config.readers.QuestDefinitionReader

class QuestDefinitionSystem : DefinitionSystem<QuestDefinition>() {

    override val reader = QuestDefinitionReader(CacheSystem.store)

}