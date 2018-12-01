package worlds.gregs.hestia.game.plugins.core.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.entity.components.update.Created
import worlds.gregs.hestia.services.Aspect

/**
 * ViewportSystem
 * Adds a player to it's own viewport & flags [Created]
 */
class ViewportSystem : SubscriptionSystem(Aspect.all(Renderable::class, Viewport::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var createdMapper: ComponentMapper<Created>

    override fun inserted(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        createdMapper.create(entityId)
        viewport.addLocalPlayer(entityId)
    }
}