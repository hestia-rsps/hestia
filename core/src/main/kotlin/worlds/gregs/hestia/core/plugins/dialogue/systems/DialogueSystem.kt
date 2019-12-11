package worlds.gregs.hestia.core.plugins.dialogue.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.api.dialogue.DialogueBase
import worlds.gregs.hestia.api.task.Task
import worlds.gregs.hestia.api.task.events.ProcessDeferral
import worlds.gregs.hestia.api.task.events.StartTask
import worlds.gregs.hestia.artemis.events.CloseDialogue
import worlds.gregs.hestia.core.plugins.dialogue.components.Dialogue
import worlds.gregs.hestia.core.plugins.task.components.TaskQueue

class DialogueSystem : DialogueBase() {

    private val logger = LoggerFactory.getLogger(DialogueSystem::class.java)
    val scripts = mutableMapOf<String, Task>()

    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    private lateinit var es: EventSystem

    override fun addDialogue(name: String, task: Task) {
        scripts[name] = task
    }

    override fun startDialogue(entityId: Int, name: String) {
        //Find base dialogue in the script
        val task = scripts[name] ?: return logger.debug("Could not find dialogue '$name'")
        //Queue the task
        es.dispatch(StartTask(entityId, task))
    }

    /**
     * Sends dialogue widget to the client
     */
    @Subscribe(priority = Int.MIN_VALUE)
    private fun handleDefer(event: ProcessDeferral) {
        val (entityId, dialogue) = event
        if(dialogue !is Dialogue) {
            es.dispatch(CloseDialogue(entityId))
        }
    }
}