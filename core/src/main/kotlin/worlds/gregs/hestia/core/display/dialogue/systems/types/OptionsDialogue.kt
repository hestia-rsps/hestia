package worlds.gregs.hestia.core.display.dialogue.systems.types

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.components.LinesDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.DialogueBaseSystem
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.game.task.DeferQueue
import worlds.gregs.hestia.game.task.TaskScope

data class OptionsDialogue(override val lines: List<String>, override val title: String?) : LinesDialogue() {

    var selected: Int? = null

    init {
        check(lines.size <= 5) { "Maximum option dialogue lines 5" }
        check(lines.size >= 2) { "Minimum option dialogue lines 2" }
    }
}

@Wire(injectInherited = true)
class OptionsDialogueSystem : DialogueBaseSystem() {

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleDefer(event: ProcessDeferral) {
        val (entityId, dialogue) = event
        if(dialogue is OptionsDialogue) {
            val interfaceId = when (dialogue.lines.size) {
                2 -> TWO_OPTIONS_ID
                3 -> THREE_OPTIONS_ID
                4 -> FOUR_OPTIONS_ID
                else -> FIVE_OPTIONS_ID
            }
            val componentStart = if (dialogue.lines.size == 3) 1 else 0
            val title = dialogue.title ?: "Select an Option"
            send(entityId, interfaceId, componentStart, title, dialogue.lines)
            event.isCancelled = true
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleContinue(event: ContinueDialogue) {
        val (entityId, _, buttonId, _) = event
        val dialogue = getDeferral(entityId) as? OptionsDialogue ?: return

        //Set choice
        dialogue.selected = buttonId

        //Continue
        taskQueue.resume(entityId)
        event.isCancelled = true
    }

}

/**
 * Helper function for [option] with just the default title
 * @param option1 The options to select between (1-5)
 * @param action1 The actions to activate when selected (1-5)
 */
suspend fun TaskScope.options(option1: String, action1: DeferQueue,
                              option2: String? = null, action2: DeferQueue? = null,
                              option3: String? = null, action3: DeferQueue? = null,
                              option4: String? = null, action4: DeferQueue? = null,
                              option5: String? = null, action5: DeferQueue? = null) =
        option(option1 = option1, action1 = action1,
                option2 = option2, action2 = action2,
                option3 = option3, action3 = action3,
                option4 = option4, action4 = action4,
                option5 = option5, action5 = action5)

/**
 * List up to 5 options and actions for the player to select from
 * @param title Optional title - Default "Select an Option"
 * @param option1 The options to select between (1-5)
 * @param action1 The actions to activate when selected (1-5)
 */
suspend fun TaskScope.option(title: String? = null,
                             option1: String, action1: DeferQueue,
                             option2: String? = null, action2: DeferQueue? = null,
                             option3: String? = null, action3: DeferQueue? = null,
                             option4: String? = null, action4: DeferQueue? = null,
                             option5: String? = null, action5: DeferQueue? = null) {
    when (option(title, *listOfNotNull(option1, option2, option3, option4, option5).toTypedArray())) {
        1 -> action1.invoke(this)
        2 -> action2?.invoke(this)
        3 -> action3?.invoke(this)
        4 -> action4?.invoke(this)
        5 -> action5?.invoke(this)
    }
}

/**
 * Helper function for [option] with just the default title
 * @param options The options to select between (1-5)
 */
suspend fun TaskScope.options(vararg options: String) = option(options = *options)

/**
 * List of up to 5 options for the player to select from
 * @param title Optional title - Default "Select an Option"
 * @param options The options to select between (1-5)
 */
suspend fun TaskScope.option(title: String? = null, vararg options: String): Int {
    val dialog = OptionsDialogue(options.toList(), title)
    deferral = dialog
    defer()
    return dialog.selected!!
}
