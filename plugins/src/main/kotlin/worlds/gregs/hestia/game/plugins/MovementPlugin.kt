package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.MOBILE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.MOVE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.NAVIGATION_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.POST_UPDATE_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_NAVIGATION_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.RUN_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.WALK_PRIORITY
import worlds.gregs.hestia.game.plugins.movement.systems.*
import worlds.gregs.hestia.game.plugins.movement.systems.calc.*
import worlds.gregs.hestia.game.plugins.movement.systems.update.MovementUpdateHandlers

class MovementPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(MovementUpdateHandlers(), PathFinderSystem(), FollowSubscriptionSystem(), StalkSubscriptionSystem())
        b.with(MOBILE_PRIORITY, MobileSystem())
        b.with(MOVE_PRIORITY, MoveSystem())
        b.with(NAVIGATION_PRIORITY, DirectStepSystem())
        b.with(NAVIGATION_PRIORITY, StepBesideSystem())
        b.with(PRE_NAVIGATION_PRIORITY, RandomWalkSystem(), StrategySystem(), PathSystem())//TODO shouldn't random walk be in it's own pre_strategy and path priority?
        b.with(WALK_PRIORITY, WalkSystem())
        b.with(RUN_PRIORITY, RunSystem())
        b.with(PRE_SHIFT_PRIORITY, FollowSystem())
        b.with(POST_SHIFT_PRIORITY, StalkSystem())
        b.with(SHIFT_PRIORITY, PositionShiftSystem())
        b.with(POST_UPDATE_PRIORITY, PostMovementSystem())
    }

}