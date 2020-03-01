package worlds.gregs.hestia.content.activity.achievement

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TaskList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TaskSystem
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.script.on

on<InterfaceInteraction> {
    where { id == TaskSystem }
    then {
        when(component) {
            99, 142, 147, 152, 157, 162 -> {//Select task
                val index = if(component == 99) 0 else (component - 137)/5
            }
            102 -> entity perform OpenInterface(TaskList)
        }
    }
}
