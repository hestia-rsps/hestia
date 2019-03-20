package worlds.gregs.hestia.game.plugins.`object`.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.api.`object`.GameObject
import worlds.gregs.hestia.api.land.Land
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.services.Aspect

class ObjectSubscriptionSystem : SubscriptionSystem(Aspect.all(GameObject::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private var land: Land? = null

    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        land?.addObject(entityId, position.xInRegion, position.yInRegion, position.plane, position.regionId)
    }

    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        land?.removeObject(entityId, position.xInRegion, position.yInRegion, position.plane, position.regionId)
    }
}