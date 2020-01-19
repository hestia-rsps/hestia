package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command

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