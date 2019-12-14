package worlds.gregs.hestia.core.display.dialogue.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.task.model.Task

abstract class DialogueBase : PassiveSystem() {

    abstract fun startDialogue(entityId: Int, name: String)

    abstract fun addDialogue(name: String, task: Task)

}