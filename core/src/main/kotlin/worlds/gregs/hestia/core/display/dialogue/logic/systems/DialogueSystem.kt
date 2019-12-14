package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.core.display.dialogue.api.DialogueBase
import worlds.gregs.hestia.core.task.model.InactiveTask
import worlds.gregs.hestia.core.task.model.ReusableTask
import worlds.gregs.hestia.core.task.model.events.StartTask

class DialogueSystem : DialogueBase() {

    private val logger = LoggerFactory.getLogger(DialogueSystem::class.java)
    val scripts = mutableMapOf<String, ReusableTask>()

    private lateinit var es: EventSystem

    override fun addDialogue(name: String, task: ReusableTask) {
        scripts[name] = task
    }

    override fun startDialogue(entityId: Int, name: String, targetId: Int?) {
        //Find base dialogue in the script
        val task = scripts[name] ?: return logger.debug("Could not find dialogue '$name'")
        //Queue the task
        es.dispatch(StartTask(entityId, InactiveTask(task, targetId)))
    }
}