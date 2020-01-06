package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.window.model.PlayerOptions.FOLLOW
import worlds.gregs.hestia.core.display.window.model.events.PlayerOption

on<PlayerOption> {
    where { option == FOLLOW }
    fun PlayerOption.task() = queue(TaskPriority.High) {
        entity follow target
    }
    then(PlayerOption::task)
}