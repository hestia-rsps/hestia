package worlds.gregs.hestia.core.display.dialogue.api

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.task.api.SuspendableQueue

abstract class DialogueBase : PassiveSystem() {

    abstract fun startDialogue(entityId: Int, name: String, targetId: Int? = null)

    abstract fun addDialogue(name: String, task: SuspendableQueue)

}