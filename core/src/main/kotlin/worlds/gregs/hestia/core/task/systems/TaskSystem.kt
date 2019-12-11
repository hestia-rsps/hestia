package worlds.gregs.hestia.core.task.systems

import com.artemis.ComponentMapper
import kotlinx.coroutines.runBlocking
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.events.CloseDialogue
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.core.task.model.events.StartTask
import worlds.gregs.hestia.game.task.DeferringCoroutine
import worlds.gregs.hestia.game.task.TaskPriority
import worlds.gregs.hestia.game.task.TaskScope
import kotlin.coroutines.EmptyCoroutineContext

/**
 *  Processes [DeferringCoroutine] calling [resume] and removing if [DeferringCoroutine.stopped]
 */
class TaskSystem : Tasks() {

    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    private lateinit var es: EventSystem

    @Subscribe
    private fun process(event: StartTask) {
        val (entityId, task) = event
        val queue = taskQueueMapper.get(entityId) ?: return

        //Clear queue if strong task
        if(task.priority == TaskPriority.Strong) {
            clear(entityId)
        }

        //Add to front of queue
        val coroutine = DeferringCoroutine(EmptyCoroutineContext, task.queue, task.priority)
        queue.addFirst(coroutine)

        //Process first
        resume(entityId, coroutine)
    }

    override fun resume(entityId: Int) = runBlocking {
        val queue = taskQueueMapper.get(entityId)
        val coroutine = queue.peek()
        resume(entityId, coroutine)
    }

    override fun clear(entityId: Int) {
        val queue = taskQueueMapper.get(entityId)
        queue.clear()
        es.dispatch(CloseDialogue(entityId))
    }

    internal fun resume(entityId: Int, coroutine: TaskScope) = runBlocking {
        val deferral = coroutine.next()

        if(coroutine.stopped()) {
            val polled = taskQueueMapper.get(entityId).poll()
            polled.stop(false)
            es.dispatch(CloseDialogue(entityId))
        } else if(deferral != null) {
            es.dispatch(ProcessDeferral(entityId, deferral))
        }
    }

}