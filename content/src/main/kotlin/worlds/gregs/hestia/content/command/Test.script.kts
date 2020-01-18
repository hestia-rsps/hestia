package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Agree
import worlds.gregs.hestia.core.display.dialogue.model.events.CloseDialogue
import worlds.gregs.hestia.core.display.dialogue.model.type.MobChat
import worlds.gregs.hestia.core.display.dialogue.model.type.PlayerChat
import worlds.gregs.hestia.core.task.model.await.Ticks

on<Command> {
    where { prefix == "test" }
    fun Command.task() = queue(2) {
        dialogue {
            entity dialogue "This is an interruption."
            entity dialogue """
                Once this task is complete,
                the previous one will resume.
            """
        }
    }
    then(Command::task)
}