package worlds.gregs.hestia.service.cache.config.systems

import worlds.gregs.hestia.service.cache.CacheSystem
import worlds.gregs.hestia.service.cache.DefinitionSystem
import worlds.gregs.hestia.service.cache.config.definitions.RenderAnimationDefinition
import worlds.gregs.hestia.service.cache.config.readers.RenderAnimationDefinitionReader

class RenderAnimationDefinitionSystem : DefinitionSystem<RenderAnimationDefinition>() {

    override val reader = RenderAnimationDefinitionReader(CacheSystem.store)

}