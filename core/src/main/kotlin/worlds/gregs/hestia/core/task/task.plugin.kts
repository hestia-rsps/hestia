package worlds.gregs.hestia.core.task

import worlds.gregs.hestia.core.task.logic.systems.*
import worlds.gregs.hestia.game.plugin.Plugin

setup {
    with(TickSuspensionSystem(), ScreenCloseSuspensionSystem())
    with(Plugin.TASK_PROCESS_PRIORITY, TaskSystem())
    with(Plugin.POST_SHIFT_PRIORITY, MovementSuspensionSystem(), DistanceSuspensionSystem())
}