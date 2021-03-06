package worlds.gregs.hestia.service.cache.definition.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionReader
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.definition.definitions.AnimationDefinition
import worlds.gregs.hestia.service.cache.definition.readers.AnimationDefinitionReader

class AnimationDefinitionSystem(override val reader: DefinitionReader<AnimationDefinition> = AnimationDefinitionReader(CacheSystem.store)) : DefinitionSystem<AnimationDefinition>()