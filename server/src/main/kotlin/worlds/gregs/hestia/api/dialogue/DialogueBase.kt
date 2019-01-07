package worlds.gregs.hestia.api.dialogue

import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.dialogue.Dialogue

abstract class DialogueBase : PassiveSystem() {

    abstract fun openChatInterface(entityId: Int, widgetId: Int)


    abstract fun startDialogue(entityId: Int, name: String)
    abstract fun stopDialogue(entityId: Int)

    abstract fun hasDialogue(entityId: Int): Boolean

    abstract fun getDialogue(entityId: Int, action: Boolean, option: Int = -1): Dialogue?
    abstract fun continueDialogue(entityId: Int, option: Int = 0)
}