package world.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import com.artemis.utils.IntBag
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.component.Renderable
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.services.exclude
import world.gregs.hestia.services.toArray

class ViewportSystem : SubscriptionSystem(Aspect.all(Renderable::class, Viewport::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun inserted(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        viewport.addLocalPlayer(entityId)
    }
}