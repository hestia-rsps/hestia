package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue


on<Command> {
    where { prefix == "test" }
    fun Command.task() = queue {
        onCancel { entity perform CloseDialogue() }

    }
    then(Command::task)
}