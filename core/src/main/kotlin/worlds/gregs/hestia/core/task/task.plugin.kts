package worlds.gregs.hestia.core.task

import worlds.gregs.hestia.core.task.logic.systems.MovementDeferralSystem
import worlds.gregs.hestia.core.task.logic.systems.ScreenCloseDeferralSystem
import worlds.gregs.hestia.core.task.logic.systems.TaskSystem
import worlds.gregs.hestia.core.task.logic.systems.TickDeferralSystem
import worlds.gregs.hestia.game.plugin.Plugin

setup {
    with(TickDeferralSystem(), TaskSystem(), ScreenCloseDeferralSystem())
    with(Plugin.POST_SHIFT_PRIORITY, MovementDeferralSystem())
}