package world.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.component.Renderable
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.update.Created

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