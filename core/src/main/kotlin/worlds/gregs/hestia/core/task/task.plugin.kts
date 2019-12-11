package worlds.gregs.hestia.core.task

import worlds.gregs.hestia.core.task.systems.MovementDeferralSystem
import worlds.gregs.hestia.core.task.systems.ScreenCloseDeferralSystem
import worlds.gregs.hestia.core.task.systems.TaskSystem
import worlds.gregs.hestia.core.task.systems.TickDeferralSystem
import worlds.gregs.hestia.game.plugin.Plugin

setup {
    with(TickDeferralSystem(), TaskSystem(), ScreenCloseDeferralSystem())
    with(Plugin.POST_SHIFT_PRIORITY, MovementDeferralSystem())
}