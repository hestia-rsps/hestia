package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.core.display.update.logic.DirectionUtils.Companion.getOffset
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.model.components.calc.Beside

/**
 * StepBesideSystem
 * Calculates the steps required to move an entity next to a position
 */
@Wire(failOnNull = false, injectInherited = true)
class StepBesideSystem : BaseMovementSystem(Beside::class) {
    private lateinit var besideMapper: ComponentMapper<Beside>

    override fun process(entityId: Int) {
        //Request to walk
        val navigate = besideMapper.get(entityId)
        load(entityId)
        //Add steps
        addSteps(entityId, navigate.x, navigate.y, navigate.max)
        //Remove request
        besideMapper.remove(entityId)
    }

    override fun addWalkStep(entityId: Int, nextX: Int, nextY: Int): Boolean {
        return addWalkStep(entityId, nextX, nextY, besideMapper.get(entityId).check)
    }

    override fun addStep(entityId: Int, myX: Int, myY: Int, destX: Int, destY: Int): IntArray? {
        val navigate = besideMapper.get(entityId)
        val newX = myX + getOffset(myX, destX)
        val newY = myY + getOffset(myY, destY)
        //If diagonal add extra step to be beside
        return if (navigate.beside && isDiagonal(myX, myY, destX, destY, navigate.sizeX, navigate.sizeY)) {
            when {
                addWalkStep(entityId, newX, myY) -> intArrayOf(newX, myY)
                addWalkStep(entityId, myX, newY) -> intArrayOf(myX, newY)
                else -> null
            }
        } else {
            when {
                addWalkStep(entityId, newX, newY) -> intArrayOf(newX, newY)
                navigate.calculate -> calculatedStep(entityId, myX, myY, destX, destY, navigate.sizeX, navigate.sizeY)
                else -> null
            }
        }
    }

    override fun hasReachedTarget(entityId: Int, myX: Int, myY: Int): Boolean {
        val navigate = besideMapper.get(entityId)
        return isNear(myX, myY, navigate.x, navigate.y, navigate.sizeX, navigate.sizeY, navigate.beside)
    }

    /**
     * Calculates an extra step if stuck on a corner
     */
    private fun calculatedStep(entityId: Int, myX: Int, myY: Int, destX: Int, destY: Int, sizeX: Int, sizeY: Int): IntArray? {
        val newX = calculate(entityId, myX, myY, destX, destY, sizeX, sizeY, true)
                ?: return if (getOffset(myX, destX) == 0) null else intArrayOf(myX + getOffset(myX, destX), myY)

        val newY = calculate(entityId, myX, myY, destX, destY, sizeX, sizeY, false)
                ?: return if (getOffset(myY, destY) == 0) null else intArrayOf(myX, myY + getOffset(myY, destY))

        //Return null if no change or the new position
        return if (newX == myX || newY == myY) {
            null
        } else {
            intArrayOf(newX, newY)
        }
    }

    /**
     * Checks +/- 1 in the direction [horizontal]
     * @return The new x/y position or null if no movement
     */
    private fun calculate(entityId: Int, myX: Int, myY: Int, destX: Int, destY: Int, sizeX: Int, sizeY: Int, horizontal: Boolean): Int? {
        //Current position x or y
        var current = if (horizontal) myX else myY
        //Current destination x/y
        val dest = if (horizontal) destX else destY

        //Get the direction of movement +/- 1
        val offset = getOffset(current, dest)

        //Apply offset to current position
        current += offset
        if (offset != 0) {
            //The new position (just myX/myY plus offset)
            val newX = if (horizontal) current else myX
            val newY = if (horizontal) myY else current
            //If can't walk in that direction
            if (!addWalkStep(entityId, newX, newY)) {
                current -= offset//Return to normal
            } else if (withinContact(newX, newY, destX, destY, sizeX, sizeY)) {//If beside destination
                return null//Done
            }
        }
        //Continue
        return current
    }

    companion object {
        fun isDiagonal(myX: Int, myY: Int, destX: Int, destY: Int, sizeX: Int, sizeY: Int): Boolean {
            val difX = myX - destX
            val difY = myY - destY
            return diagonal(difX, difY, sizeX, sizeY) || diagonal(difX, difY, -sizeX, -sizeY)
        }

        private fun diagonal(difX: Int, difY: Int, sizeX: Int, sizeY: Int): Boolean {
            //Check horizontal
            if(difX == sizeX) {
                //Check vertical
                if(difY == sizeY || difY == -sizeY) {
                    return true
                }
            }
            return false
        }

        fun isNear(position: Position, destination: Position, sizeX: Int, sizeY: Int, beside: Boolean)
                = isNear(position.x, position.y, destination.x, destination.y, sizeX, sizeY, beside)

        fun isNear(myX: Int, myY: Int, destX: Int, destY: Int, sizeX: Int, sizeY: Int, beside: Boolean): Boolean {
            //Offset by +/- 1 to include corners if not beside
            val offset = (!beside).int
            //Check horizontal and vertical contact
            return near(myY, myX, destY, destX, sizeY, sizeX, offset) || near(myX, myY, destX, destY, sizeX, sizeY, offset)
        }

        private fun near(current: Int, opposite: Int, destination: Int, oppositeDestination: Int, size: Int, oppositeSize: Int, offset: Int): Boolean {
            //Check the opposite axis position is in range of destination (incl size)
            if (opposite >= oppositeDestination - offset && opposite < oppositeDestination + oppositeSize + offset) {
                //And current position is next too destination
                if (current <= destination + size && current >= destination - 1) {
                    return true
                }
            }
            return false
        }


        fun withinContact(myX: Int, myY: Int, destX: Int, destY: Int, sizeX: Int, sizeY: Int): Boolean {
            return withinContact(myX - destX, myY - destY, sizeX, sizeY)
        }

        private fun withinContact(distanceX: Int, distanceY: Int, sizeX: Int, sizeY: Int): Boolean {
            return distanceX <= sizeX && distanceX >= -1 && distanceY <= sizeY && distanceY >= -1
        }
    }
}