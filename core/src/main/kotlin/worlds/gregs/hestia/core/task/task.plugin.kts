package worlds.gregs.hestia.core.task

import worlds.gregs.hestia.core.task.logic.systems.*
import worlds.gregs.hestia.game.plugin.Plugin

setup {
    with(TicksSystem(), InterfaceCloseSystem(), WindowCloseSystem())
    with(Plugin.TASK_PROCESS_PRIORITY, TaskSystem())
    with(Plugin.POST_TASK_PROCESS_PRIORITY, ClearTaskSystem())
    with(Plugin.POST_SHIFT_PRIORITY, RouteSystem(), WithinRangeSystem())
}