package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.BaseSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.ClearTasks
import java.util.*

class ClearTaskSystem : BaseSystem() {

    private lateinit var tasks: Tasks
    private val list = LinkedList<Int>()

    @Subscribe
    private fun clearTasks(action: ClearTasks) {
        val entityId = action.entity
        tasks.cancelAll(entityId, action.cause ?: TaskCancellation.Priority, action.priority)
        list.addLast(entityId)
    }

    override fun checkProcessing(): Boolean {
        return list.isNotEmpty()
    }

    override fun processSystem() {
        while(list.isNotEmpty()) {
            val entityId = list.poll()
            val suspension = tasks.getSuspension(entityId)
            if(suspension is ClearTasks) {
                tasks.resume(entityId, suspension, Unit)
            }
        }
    }

}