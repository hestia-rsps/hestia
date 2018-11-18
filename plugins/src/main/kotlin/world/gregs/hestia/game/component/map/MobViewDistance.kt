package world.gregs.hestia.game.component.map

import com.artemis.Component
import world.gregs.hestia.game.systems.sync.mob.MobViewDistanceSystem

class MobViewDistance : Component(), ViewDistance {
    override var distance = MobViewDistanceSystem.MAXIMUM_MOB_VIEW_DISTANCE
}