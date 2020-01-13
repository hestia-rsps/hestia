package worlds.gregs.hestia.core.task.model

import worlds.gregs.hestia.core.task.api.Task
import java.util.*

class TaskDeque(private val delegate: Deque<Task> = LinkedList()) : Deque<Task> by delegate {

    var needsUpdate = false

    override fun poll(): Task {
        needsUpdate = true
        return delegate.poll()
    }
}