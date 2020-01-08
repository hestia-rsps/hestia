package worlds.gregs.hestia.content.interaction.`object`

import worlds.gregs.hestia.core.entity.`object`.model.events.ObjectOption

on<ObjectOption> {
    where { option == "Use" && name == "Counter" }
    fun ObjectOption.task() = queue(TaskPriority.High) {
        entity.interact(target, 0)
        TODO("Open bank")
    }
    then(ObjectOption::task)
}