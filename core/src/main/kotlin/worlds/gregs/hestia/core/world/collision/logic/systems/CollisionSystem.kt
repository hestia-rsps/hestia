package worlds.gregs.hestia.core.world.collision.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.collision.api.EntityCollision
import worlds.gregs.hestia.core.world.collision.api.ObjectCollision

@Wire(failOnNull = false)
class CollisionSystem : Collision() {
    private var entityCollision: EntityCollision? = null
    private var objectCollision: ObjectCollision? = null

    private lateinit var positionMapper: ComponentMapper<Position>
    private var baseX = 0
    private var baseY = 0

    override fun load(entityId: Int) {
        val position = positionMapper.get(entityId)

        baseX = position.xInRegion - 64
        baseY = position.yInRegion - 64
        entityCollision?.load(entityId, position)
        objectCollision?.load(position)
    }

    override fun collides(x: Int, y: Int, flag: Int): Boolean {
        if(entityCollision?.collides(x, y, flag) == true) {
            return true
        }

        if(objectCollision?.collides(x, y, flag) == true) {
            return true
        }

        return false
    }

}