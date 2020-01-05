package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.window.model.PlayerOptions.FOLLOW
import worlds.gregs.hestia.core.display.window.model.events.PlayerOption
import worlds.gregs.hestia.core.task.api.event.target

on<PlayerOption> {
    where { option == FOLLOW }
    task(TaskPriority.High) {
        entity follow target
    }
}