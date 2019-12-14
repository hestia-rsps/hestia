package worlds.gregs.hestia.core.world.movement.api.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.service.Aspect
import kotlin.reflect.KClass

abstract class RegionSubscription(vararg classes: KClass<out Component>) : SubscriptionSystem(Aspect.all(Position::class, *classes)) {

    private lateinit var positionMapper: ComponentMapper<Position>

    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        changeRegion(entityId, null, position.regionId)
    }

    override fun removed(entityId: Int) {
        val position = positionMapper.get(entityId)
        changeRegion(entityId, position.regionId, null)
    }

    abstract fun changeRegion(entityId: Int, from: Int?, to: Int?)
}