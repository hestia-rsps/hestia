package worlds.gregs.hestia.core.task.tick.logic

import com.artemis.BaseSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.core.task.tick.logic.TickTaskSystem.TaskWrapper
import worlds.gregs.hestia.core.task.tick.model.TickTask
import worlds.gregs.hestia.core.task.tick.model.TickTaskEvent
import java.util.*

/**
 * Technically a separate system from [TaskWrapper] but it's the most relevant location
 */
class TickTaskSystem : BaseSystem() {

    private val tasks = Collections.synchronizedList(LinkedList<TaskWrapper>())

    val tasksCount: Int
        get() = tasks.size

    override fun checkProcessing(): Boolean {
        return tasksCount > 0
    }

    override fun processSystem() {
        for (task in tasks.toTypedArray()) {
            //Delay
            if (task.delay > 0) {
                task.delay--
                continue
            }

            //Task
            task.task.run()

            //Remove if inactive
            if (!task.task.running) {
                tasks.remove(task)
            } else {
                //Reset delay
                task.delay = task.period - 1
            }
        }
    }
    
    @Subscribe
    fun schedule(event: TickTaskEvent) {
        schedule(event.delay, event.period, event.task)
    }

    /**
     * Schedules a tick task
     * @param delay the number of ticks to wait before starting (0 is instant)
     * @param period how often to repeat the task until stopped (0 doesn't repeat)
     * @param task the task to run
     */
    fun schedule(delay: Int, period: Int, task: TickTask.() -> Unit) {
        if(delay < 0 || period < 0) {
            return
        }
        tasks.add(TaskWrapper(TickTask(period != 0, task), delay, period))
    }

    private data class TaskWrapper(val task: TickTask, var delay: Int, val period: Int)
}