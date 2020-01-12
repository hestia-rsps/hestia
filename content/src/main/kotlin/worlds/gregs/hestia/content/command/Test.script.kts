package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.window.model.events.SetVariable


on<Command> {
    where { prefix == "test" }

    fun Command.task() = queue {
        onCancel { entity perform CloseDialogue() }

//        entity perform OpenWindow(Windows.AssistXP)
        entity perform SetVariable("energy_orb", content.toInt())
//        entity perform AddVariable("event_emotes", "Skillcape")
//        entity perform RemoveVariable("event_emotes", "Freeze")
//        entity send Varp(1160, content.toInt())
//        entity perform RemoveVariable("event_emotes", "Freeze")

//        entity openWindow AssistXP
//        entity perform Experience(CRAFTING, 10000)
    }
    then(Command::task)
}