package worlds.gregs.hestia.game.api.movement

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.api.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.services.Aspect
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