package worlds.gregs.hestia.core.display.dialogue.systems.types

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.events.StringEntered
import worlds.gregs.hestia.core.display.dialogue.components.Dialogue
import worlds.gregs.hestia.core.display.dialogue.systems.DialogueBaseSystem
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.network.client.encoders.messages.Script
import worlds.gregs.hestia.services.send

data class StringEntryDialogue(val title: String) : Dialogue {
    var entry: String? = null
}

suspend fun TaskScope.stringEntry(title: String): String {
    val dialog = StringEntryDialogue(title)
    deferral = dialog
    defer()
    return dialog.entry!!
}

@Wire(injectInherited = true)
class StringEntryDialogueSystem : DialogueBaseSystem() {

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleDefer(event: ProcessDeferral) {
        val (entityId, dialogue) = event
        if(dialogue is StringEntryDialogue) {
            es.send(entityId, Script(108, dialogue.title))
            event.isCancelled = true
        }
    }

    @Subscribe
    private fun handleInput(event: StringEntered) {
        val (entityId, text) = event
        val dialogue = getDeferral(entityId) as? StringEntryDialogue ?: return
        //Set string entered
        dialogue.entry = text
        //Continue
        taskQueue.resume(entityId)
    }

}