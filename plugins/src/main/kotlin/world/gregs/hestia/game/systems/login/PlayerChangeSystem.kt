package world.gregs.hestia.game.systems.login

import com.artemis.ComponentMapper
import com.artemis.utils.IntBag
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.component.Renderable
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.services.exclude
import world.gregs.hestia.services.toArray

class PlayerChangeSystem : SubscriptionSystem(Aspect.all(Renderable::class, Viewport::class).exclude(Mob::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun inserted(entities: IntBag) {
        entityIds.toArray().forEach { entityId ->
            val viewport = viewportMapper.get(entityId)
            for(i in 0 until entities.size()) {
                val addedId = entities.get(i)
                if(addedId == entityId) {
                    viewport.addLocalPlayer(addedId)
                } else {
                    viewport.addGlobalPlayer(addedId)
                }
            }
        }
    }
}