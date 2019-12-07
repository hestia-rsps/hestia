package worlds.gregs.hestia.core.plugins.core.systems

import com.artemis.BaseSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.events.TickTaskEvent
import worlds.gregs.hestia.game.TickTask
import java.util.*

class TickTaskSystem : BaseSystem() {

    private val tasks = Collections.synchronizedList(LinkedList<Task>())

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
        tasks.add(Task(TickTask(period != 0, task), delay, period))
    }

    private data class Task(val task: TickTask, var delay: Int, val period: Int)
}