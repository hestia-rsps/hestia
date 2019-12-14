package worlds.gregs.hestia.core.task.model.components

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.annotations.PooledWeaver
import kotlinx.coroutines.runBlocking
import worlds.gregs.hestia.game.task.TaskScope
import java.util.*

/**
 * A queue of active tasks
 */
@PooledWeaver
class TaskQueue : Component() {
    val queue = LinkedList<TaskScope>()

    fun peek() = queue.peek()

    fun poll() = queue.poll()

    fun addFirst(scope: TaskScope) = queue.addFirst(scope)

    fun clear() {
        runBlocking {
            queue.forEach { it.stop(false) }
        }
        queue.clear()
    }
}

fun ComponentMapper<TaskQueue>.getDeferral(entityId: Int) = get(entityId)?.peek()?.deferral