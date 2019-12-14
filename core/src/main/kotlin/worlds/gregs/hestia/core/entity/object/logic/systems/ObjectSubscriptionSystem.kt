package worlds.gregs.hestia.core.entity.`object`.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.world.land.api.Land
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.service.Aspect

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