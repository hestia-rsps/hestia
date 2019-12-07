package worlds.gregs.hestia.api.dialogue

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.task.Task

abstract class DialogueBase : PassiveSystem() {

    abstract fun startDialogue(entityId: Int, name: String)

    abstract fun addDialogue(name: String, task: Task)

}