package worlds.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.game.component.movement.Navigate
import worlds.gregs.hestia.game.component.movement.Steps
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getMoveDirection
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getOffset

/**
 * Navigation system
 * Calculates the steps required for an entity to reach a position
 */
class NavigationSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class, Navigate::class)) {
    private lateinit var navigateMapper: ComponentMapper<Navigate>

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepsMapper: ComponentMapper<Steps>

    override fun process(entityId: Int) {
        //Request to walk
        val navigate = navigateMapper.get(entityId)
        //Add steps
        addWalkSteps(entityId, navigate.x, navigate.y, navigate.max, navigate.check)
        //Remove request
        navigateMapper.remove(entityId)
    }


    /**
     * Navigate directly to the destination
     * @param check Stop at obstacles or no-clip through everything
     */
    private fun addWalkSteps(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int, check: Boolean): Boolean {
        val addable: (Int, Int, Int, Int) -> IntArray? = { myX, myY, lastX, lastY ->
            val offsetX = getOffset(myX, destX)
            val offsetY = getOffset(myY, destY)
            if (!addWalkStep(entityId, stepsMapper, myX + offsetX, myY + offsetY, lastX, lastY, check)) {
                null
            } else {
                intArrayOf(myX + offsetX, myY + offsetY)
            }
        }

        return addSteps(entityId, stepsMapper, positionMapper, destX, destY, maxStepsCount, addable, null)
    }

    companion object {

        fun addSteps(entityId: Int, stepsMapper: ComponentMapper<Steps>, positionMapper: ComponentMapper<Position>, destX: Int, destY: Int, maxStepsCount: Int, addable: (Int, Int, Int, Int) -> IntArray?, success: ((Int, Int) -> Boolean)?): Boolean {
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

        fun addWalkStep(entityId: Int, stepsMapper: ComponentMapper<Steps>, nextX: Int, nextY: Int, lastX: Int, lastY: Int, check: Boolean = true): Boolean {
            val dir = getMoveDirection(nextX - lastX, nextY - lastY)
            if (dir == -1) {
                return false
            }

            /*if (check && (entity == null || !entity!!.world.checkWalkStep(entity!!.plane, lastX, lastY, dir, entity!!.size))) {
                return false
            }*/

            val steps = stepsMapper.create(entityId)
            steps.add(dir)
            steps.lastX = nextX
            steps.lastY = nextY
            return true
        }
    }

}