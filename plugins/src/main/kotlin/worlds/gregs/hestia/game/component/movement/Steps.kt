package worlds.gregs.hestia.game.component.movement

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import java.util.concurrent.ConcurrentLinkedQueue

@PooledWeaver
class Steps : Component() {
    var lastX: Int? = null
    var lastY: Int? = null
    private val directions = ConcurrentLinkedQueue<Int>()

    val hasNext: Boolean
        get() = directions.isNotEmpty()

    val nextDirection: Int
        get() {
            return directions.poll()
        }

    fun add(step: Int) {
        directions.add(step)
    }

    fun clear() {
        directions.clear()
    }

}