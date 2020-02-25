package worlds.gregs.hestia.service.cache.config.systems

import world.gregs.hestia.cache.definition.config.definitions.RenderAnimationDefinition
import world.gregs.hestia.cache.definition.config.readers.RenderAnimationDefinitionReader
import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem

class RenderAnimationDefinitionSystem : DefinitionSystem<RenderAnimationDefinition>() {

    override val reader = RenderAnimationDefinitionReader(CacheSystem.store)

}