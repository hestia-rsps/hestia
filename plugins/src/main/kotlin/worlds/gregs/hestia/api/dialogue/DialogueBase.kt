package worlds.gregs.hestia.api.dialogue

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.plugins.dialogue.components.DialogueQueue

abstract class DialogueBase : PassiveSystem() {

    abstract fun startDialogue(entityId: Int, name: String)
    abstract fun startDialogue(entityId: Int, block: DialogueQueue)
    abstract fun addDialogue(name: String, block: DialogueQueue)
    abstract fun stopDialogue(entityId: Int)
    abstract fun hasDialogue(entityId: Int): Boolean

}