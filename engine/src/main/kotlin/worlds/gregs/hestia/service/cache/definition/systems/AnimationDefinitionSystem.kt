package worlds.gregs.hestia.service.cache.definition.systems

import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.cache.definition.definitions.AnimationDefinition
import world.gregs.hestia.cache.definition.readers.AnimationDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class AnimationDefinitionSystem(override val reader: DefinitionReader<AnimationDefinition> = AnimationDefinitionReader(CacheSystem.store)) : DefinitionSystem<AnimationDefinition>()