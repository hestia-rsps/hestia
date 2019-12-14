package worlds.gregs.hestia.content.interaction.player

import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.player.model.events.PlayerOption
import worlds.gregs.hestia.core.script.dsl.task.ChatType.Assist
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameAssist
import worlds.gregs.hestia.core.script.dsl.task.PlayerOptions.ASSIST
import worlds.gregs.hestia.core.task.api.event.target

on<PlayerOption> {
    where { option == ASSIST }
    task(TaskPriority.High) {
        entity distance 1 interact target
        entity type GameAssist message "Sending assistance request."
        val name = (entity get DisplayName::class).name!!
        target type Assist name name message "is requesting your assistance."
        //TODO("Accept")
//        target message "Sending assistance response."
//        target message "You are assisting $name."
//        target message "It has been 24 hours since you first helped someone using the Assist System."
//        target message "You can now use it to gain the full amount of XP."
        entity type GameAssist message "You are being assisted by $name."
    }
}