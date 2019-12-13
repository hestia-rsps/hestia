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
    private val position = Position.clone(Position.EMPTY)
    private var step = false
    private var baseX = 0
    private var baseY = 0

    override fun load(entityId: Int, step: Boolean, x: Int?, y: Int?) {
        val position = positionMapper.get(entityId)
        this.step = step

        if(x != null && y != null) {
            this.position.set(x, y, position.plane)
        }

        val pos = if(x != null && y != null) this.position else position
        baseX = pos.xInRegion - 64
        baseY = pos.yInRegion - 64
        entityCollision?.load(entityId, position)
        objectCollision?.load(pos, step)
    }

    override fun collides(x: Int, y: Int, mask: Int): Boolean {
        //Correct for pathfinder offset
        if(entityCollision?.collides(if(step) x else x + baseX, if(step) y else y + baseY) == true) {
            return true
        }

        if(objectCollision?.collides(x, y, mask) == true) {
            return true
        }

        return false
    }

}