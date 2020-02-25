package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.QuickChatOptionDefinition
import world.gregs.hestia.cache.definition.readers.QuickChatOptionDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class QuickChatOptionDefinitionSystem(override val reader: DefinitionReader<QuickChatOptionDefinition> = QuickChatOptionDefinitionReader(CacheSystem.store)) : DefinitionSystem<QuickChatOptionDefinition>()