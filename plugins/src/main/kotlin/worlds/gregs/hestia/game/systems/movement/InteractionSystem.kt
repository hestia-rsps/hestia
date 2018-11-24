package worlds.gregs.hestia.game.systems.movement

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.movement.Interact
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.game.component.movement.Steps
import worlds.gregs.hestia.game.systems.movement.NavigationSystem.Companion.addSteps
import worlds.gregs.hestia.game.systems.movement.NavigationSystem.Companion.addWalkStep
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getOffset

/**
 * Interaction system
 * Calculates the steps required to move an entity next to a position
 */
class InteractionSystem : IteratingSystem(Aspect.all(Position::class, Mobile::class, Interact::class)) {
    private lateinit var interactMapper: ComponentMapper<Interact>

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var stepsMapper: ComponentMapper<Steps>

    override fun process(entityId: Int) {
        //Request to walk
        val navigate = interactMapper.get(entityId)
        //Add steps
        addWalkStepsInteract(entityId, navigate.x, navigate.y, navigate.max, navigate.sizeX, navigate.sizeY, navigate.check, navigate.calculate)
        //Remove request
        interactMapper.remove(entityId)
    }

    /**
     * Navigate directly to the tile next to the (sized) target
     * @param calculate Whether or not to calculate an extra step to prevent getting caught on corners
     */
    private fun addWalkStepsInteract(entityId: Int, destX: Int, destY: Int, maxStepsCount: Int, sizeX: Int, sizeY: Int, check: Boolean, calculate: Boolean): Boolean {
        val addable: (Int, Int, Int, Int) -> IntArray? = success@{ myX, myY, lastX, lastY ->
            val newX = myX + getOffset(myX, destX)
            val newY = myY + getOffset(myY, destY)
            if (!addWalkStep(entityId, stepsMapper, newX, newY, lastX, lastY, check)) {
                if(!calculate) {
                    null
                } else {
                    val step = calculatedStep(entityId, myX, myY, destX, destY, lastX, lastY, sizeX, sizeY, check)
                            ?: return@success null
                    step
                }
            } else {
                intArrayOf(newX, newY)
            }
        }

        val success: (Int, Int) -> Boolean = { myX, myY -> withinContact(myX, myY, destX, destY, sizeX, sizeY) }

        return addSteps(entityId, stepsMapper, positionMapper, destX, destY, maxStepsCount, addable, success)
    }

    /**
     * Calculates the extra step if stuck on a corner
     */
    private fun calculatedStep(entityId: Int, myX: Int, myY: Int, destX: Int, destY: Int, lastX: Int, lastY: Int, sizeX: Int, sizeY: Int, check: Boolean): IntArray? {

        val newX = calculate(entityId, myX, myY, destX, destY, lastX, lastY, sizeX, sizeY, check, true)
                ?: return step(myX + getOffset(myX, destX), myY, lastX, lastY)

        val newY = calculate(entityId, myX, myY, destX, destY, lastX, lastY, sizeX, sizeY, check, false)
                ?: return step(myX, myY + getOffset(myY, destY), lastX, lastY)

        return step(newX, newY, lastX, lastY)
    }

    /**
     * Calculates x/y change
     */
    private fun calculate(entityId: Int, myX: Int, myY: Int, destX: Int, destY: Int, lastX: Int, lastY: Int, sizeX: Int, sizeY: Int, check: Boolean, horizontal: Boolean): Int? {
        var current = if (horizontal) myX else myY
        val dest = if (horizontal) destX else destY

        val offset = getOffset(current, dest)

        current += offset
        if (current != dest) {
            val newX = if (horizontal) current else myX
            val newY = if (horizontal) myY else current
            if (!addWalkStep(entityId, stepsMapper, newX, newY, lastX, lastY, check)) {
                current -= offset
            } else if (withinContact(newX, newY, destX, destY, sizeX, sizeY)) {
                return null
            }
        }
        return current
    }

    private fun step(myX: Int, myY: Int, lastX: Int, lastY: Int): IntArray? {
        return if (myX == lastX || myY == lastY) {
            null
        } else {
            intArrayOf(myX, myY)
        }
    }

    private fun withinContact(myX: Int, myY: Int, destX: Int, destY: Int, sizeX: Int, sizeY: Int): Boolean {
        return withinContact(myX - destX, myY - destY, sizeX, sizeY)
    }

    private fun withinContact(distanceX: Int, distanceY: Int, sizeX: Int, sizeY: Int): Boolean {
        return !(distanceX > sizeX || distanceX < -1 || distanceY > sizeY || distanceY < -1)
    }
}