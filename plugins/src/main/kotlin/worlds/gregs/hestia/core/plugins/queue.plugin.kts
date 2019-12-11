package worlds.gregs.hestia.core.plugins

import worlds.gregs.hestia.core.plugins.task.systems.*
import worlds.gregs.hestia.game.plugin.Plugin

setup {
    with(TickDeferralSystem(), TaskSystem(), ScreenCloseDeferralSystem())
    with(Plugin.POST_SHIFT_PRIORITY, MovementDeferralSystem())
}