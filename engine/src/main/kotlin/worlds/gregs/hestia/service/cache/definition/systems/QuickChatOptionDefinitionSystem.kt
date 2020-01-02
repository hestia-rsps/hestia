package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.QuickChatOptionDefinition
import worlds.gregs.hestia.service.cache.definition.readers.QuickChatOptionDefinitionReader

class QuickChatOptionDefinitionSystem(override val reader: DefinitionReader<QuickChatOptionDefinition> = QuickChatOptionDefinitionReader(CacheSystem.store)) : DefinitionSystem<QuickChatOptionDefinition>()