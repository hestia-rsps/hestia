package worlds.gregs.hestia.core.task.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.model.InactiveTask
import java.util.*

class TaskQueue : Component() {
    val inactiveTasks: Deque<InactiveTask> = LinkedList<InactiveTask>()
    val active = TreeMap<Int, Deque<Task>>(Collections.reverseOrder())
    var needsUpdate = false
}