package world.gregs.hestia.game.component.map

import com.artemis.Component
import world.gregs.hestia.game.systems.sync.player.PlayerViewDistanceSystem

class PlayerViewDistance : Component(), ViewDistance {
    override var distance = PlayerViewDistanceSystem.MAXIMUM_PLAYER_VIEW_DISTANCE
}