package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.world.map.api.TileCollision
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils.Companion.getMoveDirection
import worlds.gregs.hestia.artemis.Aspect
import kotlin.reflect.KClass

/**
 * Navigation system
 * Calculates the steps required for an entity to reach a position
 */

abstract class BaseMovementSystem(vararg classes: KClass<out Component>) : IteratingSystem(Aspect.all(Position::class, Mobile::class, *classes)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private var tiles: TileCollision? = null
    private var collision: Collision? = null

    fun load(entityId: Int) {
        collision?.load(entityId)
    }

    protected fun addSteps(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int): Boolean {
        val steps = stepsMapper.get(entityId)
        val position = positionMapper.get(entityId)
        var myX = steps?.lastX ?: position.x
        var myY = steps?.lastY ?: position.y

        var stepCount = 0
        while (true) {
            stepCount++

            val change = addStep(entityId, myX, myY, destX, destY)
            if (change?.isNotEmpty() == true) {
                myX = change[0]
                myY = change[1]
            } else {
                return false
            }

            if (hasReachedTarget(entityId, myX, myY) || stepCount == maxStepsCount) {
                return true
            }

            if (myX == destX && myY == destY) {
                return true
            }
        }
    }

    open fun addStep(entityId: Int, myX: Int, myY: Int, destX: Int, destY: Int): IntArray? {
        val offsetX = DirectionUtils.getOffset(myX, destX)
        val offsetY = DirectionUtils.getOffset(myY, destY)
        return if (addWalkStep(entityId, myX + offsetX, myY + offsetY)) {
            intArrayOf(myX + offsetX, myY + offsetY)
        } else {
            null
        }
    }

    open fun hasReachedTarget(entityId: Int, myX: Int, myY: Int): Boolean {
        return false
    }


    open fun addWalkStep(entityId: Int, nextX: Int, nextY: Int): Boolean {
        return addWalkStep(entityId, nextX, nextY, true)
    }

    protected fun addWalkStep(entityId: Int, nextX: Int, nextY: Int, check: Boolean): Boolean {
        val position = positionMapper.get(entityId)
        val steps = stepsMapper.get(entityId)
        val lastX = steps.lastX ?: position.x
        val lastY = steps.lastY ?: position.y

        val dir = getMoveDirection(nextX, nextY, lastX, lastY)
        if(dir == null || dir == Direction.NONE) {
            return false
        }

        if(check) {// TODO step should always check otherwise we'll start walking through doors
            if (!world.entityManager.isActive(entityId) || (tiles != null && !tiles!!.traversable(dir, lastX, lastY, position.plane, sizeMapper.width(entityId), sizeMapper.height(entityId)))) {
                return false
            }
        }

        steps.add(dir, nextX, nextY)
        return true
    }

}