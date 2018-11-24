package worlds.gregs.hestia.game.player.component

import com.artemis.Component
import worlds.gregs.hestia.game.component.map.ViewDistance
import worlds.gregs.hestia.game.player.systems.sync.PlayerViewDistanceSystem

class PlayerViewDistance : Component(), ViewDistance {
    override var distance = PlayerViewDistanceSystem.MAXIMUM_PLAYER_VIEW_DISTANCE
}