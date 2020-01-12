package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.systems.IteratingSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.ClearTasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue

class ClearTaskSystem : IteratingSystem(Aspect.all(TaskQueue::class)) {

    private lateinit var tasks: Tasks

    @Subscribe
    private fun clearTasks(action: ClearTasks) {
        val entityId = action.entity
        tasks.cancelAll(entityId, TaskCancellation.Priority)
    }

    override fun process(entityId: Int) {
        val suspension = tasks.getSuspension(entityId)
        if(suspension is ClearTasks) {
            tasks.resume(entityId, suspension, Unit)
        }
    }

}