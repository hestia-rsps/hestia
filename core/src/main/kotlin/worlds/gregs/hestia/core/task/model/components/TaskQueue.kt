package worlds.gregs.hestia.core.task.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.TaskDeque
import java.util.*

class TaskQueue : Component() {
    val inactiveTasks: Deque<InactiveTask> = LinkedList<InactiveTask>()
    val active = TreeMap<Int, TaskDeque>(Collections.reverseOrder())
}