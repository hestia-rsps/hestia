package worlds.gregs.hestia.content.interaction.`object`

import worlds.gregs.hestia.core.entity.`object`.model.events.ObjectOption
import worlds.gregs.hestia.core.task.api.event.target

on<ObjectOption> {
    where { option == "Use" && name == "Counter" }
    task(TaskPriority.High) {
        entity distance 0 interact target
        TODO("Open bank")
    }
}