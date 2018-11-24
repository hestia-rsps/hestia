package worlds.gregs.hestia.game.mob.component

import com.artemis.Component
import worlds.gregs.hestia.game.component.map.ViewDistance
import worlds.gregs.hestia.game.mob.systems.sync.MobViewDistanceSystem

class MobViewDistance : Component(), ViewDistance {
    override var distance = MobViewDistanceSystem.MAXIMUM_MOB_VIEW_DISTANCE
}