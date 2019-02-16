package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.entity.Size
import worlds.gregs.hestia.game.entity.height
import worlds.gregs.hestia.game.entity.width
import worlds.gregs.hestia.api.map.TileClipping
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.update.Direction
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getMoveDirection
import worlds.gregs.hestia.services.Aspect
import kotlin.reflect.KClass

/**
 * Navigation system
 * Calculates the steps required for an entity to reach a position
 */

abstract class BaseMovementSystem(vararg classes: KClass<out Component>) : IteratingSystem(Aspect.all(Position::class, Mobile::class, *classes)) {
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepsMapper: ComponentMapper<Steps>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private var tiles: TileClipping? = null
    private var collision: Collision? = null

    /**
     * Navigate directly to the destination
     * @param check Stop at obstacles or no-clip through everything
     */
    protected fun addWalkSteps(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int, check: Boolean): Boolean {
        val addable: (Int, Int) -> IntArray? = { myX, myY ->
            val offsetX = DirectionUtils.getOffset(myX, destX)
            val offsetY = DirectionUtils.getOffset(myY, destY)
            if (addWalkStep(entityId, myX + offsetX, myY + offsetY, check)) {
                intArrayOf(myX + offsetX, myY + offsetY)
            } else {
                null
            }
        }

        return addSteps(entityId, destX, destY, maxStepsCount, addable, null)
    }

    protected fun addSteps(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int, addable: (Int, Int) -> IntArray?, success: ((Int, Int) -> Boolean)?): Boolean {
        val steps = stepsMapper.get(entityId)
        val position = positionMapper.get(entityId)
        var myX = steps?.lastX ?: position.x
        var myY = steps?.lastY ?: position.y

        var stepCount = 0
        while (true) {
            stepCount++

            val change = addable(myX, myY)
            if (change?.isNotEmpty() == true) {
                myX = change[0]
                myY = change[1]
            } else {
                return false
            }

            if (success?.invoke(myX, myY) == true || stepCount == maxStepsCount) {
                return true
            }

            if (myX == destX && myY == destY) {
                return true
            }
        }
    }

    protected fun addWalkStep(entityId: Int, nextX: Int, nextY: Int, check: Boolean = true): Boolean {
        val position = positionMapper.get(entityId)
        var lastX = position.x
        var lastY= position.y

        //TODO sort out, this should be before position
        if(stepsMapper.has(entityId)) {
            val steps = stepsMapper.get(entityId)
            lastX = steps.lastX ?: lastX
            lastY = steps.lastY ?: lastY
        }

        val dir = getMoveDirection(nextX, nextY, lastX, lastY)
        if(dir == null || dir == Direction.NONE) {
            return false
        }

        if(check) {
            collision?.load(entityId, true, lastX, lastY)//TODO move somewhere higher up, is it even needed all the time?

            if (!world.entityManager.isActive(entityId) || (tiles != null && !tiles!!.traversable(dir, lastX, lastY, position.plane, sizeMapper.width(entityId), sizeMapper.height(entityId)))) {
                return false
            }
        }

        val steps = stepsMapper.create(entityId)
        steps.add(dir, nextX, nextY)
        return true
    }

}