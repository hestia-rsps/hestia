package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.api.map.TileClipping
import worlds.gregs.hestia.game.plugins.core.components.entity.Size
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.Steps
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

    /**
     * Navigate directly to the destination
     * @param check Stop at obstacles or no-clip through everything
     */
    protected fun addWalkSteps(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int, check: Boolean): Boolean {
        val addable: (Int, Int, Int, Int) -> IntArray? = { myX, myY, lastX, lastY ->
            val offsetX = DirectionUtils.getOffset(myX, destX)
            val offsetY = DirectionUtils.getOffset(myY, destY)
            if (!addWalkStep(entityId, myX + offsetX, myY + offsetY, lastX, lastY, check)) {
                null
            } else {
                intArrayOf(myX + offsetX, myY + offsetY)
            }
        }

        return addSteps(entityId, destX, destY, maxStepsCount, addable, null)
    }

    protected fun addSteps(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int, addable: (Int, Int, Int, Int) -> IntArray?, success: ((Int, Int) -> Boolean)?): Boolean {
        val steps = stepsMapper.get(entityId)
        val position = positionMapper.get(entityId)
        var lastX = steps?.lastX ?: position.x
        var lastY = steps?.lastY ?: position.y
        var myX = lastX
        var myY = lastY

        var stepCount = 0
        while (true) {
            stepCount++

            val change = addable(myX, myY, lastX, lastY)
            if (change?.isNotEmpty() == true) {
                myX = change[0]
                myY = change[1]
            } else {
                return false
            }

            if (success?.invoke(myX, myY) == true || stepCount == maxStepsCount) {
                return true
            }

            lastX = myX
            lastY = myY
            if (lastX == destX && lastY == destY) {
                return true
            }
        }
    }

    protected fun addWalkStep(entityId: Int, nextX: Int, nextY: Int, lastX: Int, lastY: Int, check: Boolean = true): Boolean {
        val dir = getMoveDirection(nextX - lastX, nextY - lastY)
        if (dir == -1) {
            return false
        }

        val position = positionMapper.get(entityId)
        val sizeX = if(sizeMapper.has(entityId)) sizeMapper.get(entityId).sizeX else 1
        val sizeY = if(sizeMapper.has(entityId)) sizeMapper.get(entityId).sizeY else 1
        if (check && (!world.entityManager.isActive(entityId) || (tiles != null && !tiles!!.checkWalkStep(position.plane, lastX, lastY, dir, sizeX, sizeY)))) {
            return false
        }

        val steps = stepsMapper.create(entityId)
        steps.add(dir)
        steps.lastX = nextX
        steps.lastY = nextY
        return true
    }

}