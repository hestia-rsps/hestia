package worlds.gregs.hestia.game.plugins.player.component

import com.artemis.Component
import worlds.gregs.hestia.api.core.ViewDistance
import worlds.gregs.hestia.game.plugins.player.systems.sync.PlayerViewDistanceSystem

class PlayerViewDistance : Component(), ViewDistance {
    override var distance = PlayerViewDistanceSystem.MAXIMUM_PLAYER_VIEW_DISTANCE
}