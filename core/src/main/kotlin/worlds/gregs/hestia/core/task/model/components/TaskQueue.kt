package worlds.gregs.hestia.core.task.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.context.EntityContext
import java.util.*

class TaskQueue : Component() {
    val inactiveTasks: Deque<InactiveTask<*>> = LinkedList()
    val active: Deque<Task> = LinkedList()

    lateinit var context: EntityContext
}