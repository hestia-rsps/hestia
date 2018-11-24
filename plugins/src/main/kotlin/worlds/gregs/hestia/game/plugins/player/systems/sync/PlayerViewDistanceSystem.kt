package worlds.gregs.hestia.game.plugins.player.systems.sync

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem
import worlds.gregs.hestia.game.plugins.player.component.PlayerViewDistance

class PlayerViewDistanceSystem : ViewDistanceSystem(PlayerChunkSystem::class, MAXIMUM_PLAYER_VIEW_DISTANCE, MAXIMUM_LOCAL_PLAYERS, PlayerViewDistance::class) {

    private lateinit var playerViewDistanceMapper: ComponentMapper<PlayerViewDistance>

    override fun getMapper(): ComponentMapper<out Component> {
        return playerViewDistanceMapper
    }

    companion object {
        const val MAXIMUM_PLAYER_VIEW_DISTANCE = 15
        const val MAXIMUM_LOCAL_PLAYERS = 255
    }
}
