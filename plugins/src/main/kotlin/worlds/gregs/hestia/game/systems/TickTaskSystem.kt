package worlds.gregs.hestia.game.systems

import com.artemis.BaseSystem
import worlds.gregs.hestia.game.events.TaskEvent
import net.mostlyoriginal.api.event.common.Subscribe
import java.util.*
import worlds.gregs.hestia.game.TickTask

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
                task.delay = task.period
            }
        }
    }
    
    @Subscribe
    fun schedule(event: TaskEvent) {
        if(event.delay < 0 || event.period < 0) {
            return
        }
        tasks.add(Task(TickTask(event.period != 0, event.task), event.delay, event.period))
    }
    
    private data class Task(val task: TickTask, var delay: Int, val period: Int)
}