package worlds.gregs.hestia.content.activity.achievement

import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TaskList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TaskSystem
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface

on(InterfaceOption, "Select Task", id = TaskSystem) { hash, _, _, _ ->
    val component = getInterfaceComponentId(hash)
    val index = if(component == 99) 0 else (component - 137)/5
}

on(InterfaceOption, "Open", id = TaskSystem) { _, _, _, _ ->
    entity perform OpenInterface(TaskList)
}
