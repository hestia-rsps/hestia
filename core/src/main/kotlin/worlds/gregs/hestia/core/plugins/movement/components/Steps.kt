package worlds.gregs.hestia.core.plugins.movement.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.client.update.block.Direction
import java.util.concurrent.ConcurrentLinkedQueue

@PooledWeaver
class Steps : Component() {
    var lastX: Int? = null
    var lastY: Int? = null

    private val directions = ConcurrentLinkedQueue<Direction>()

    val hasNext: Boolean
        get() = directions.isNotEmpty()

    val nextDirection: Direction
        get() {
            return directions.poll()
        }

    val peek: Direction
        get() {
            return directions.peek() ?: Direction.NONE
        }

    fun add(direction: Direction, nextX: Int, nextY: Int) {
        directions.add(direction)
        lastX = nextX
        lastY = nextY
    }

    fun clear() {
        directions.clear()
        lastX = null
        lastY = null
    }
}