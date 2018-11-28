package worlds.gregs.hestia.game.plugins.mob.component

import com.artemis.Component
import worlds.gregs.hestia.game.plugins.core.components.map.ViewDistance
import worlds.gregs.hestia.game.plugins.mob.systems.sync.MobViewDistanceSystem

class MobViewDistance : Component(), ViewDistance {
    override var distance = MobViewDistanceSystem.MAXIMUM_MOB_VIEW_DISTANCE
}