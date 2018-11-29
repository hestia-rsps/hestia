package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.player.systems.map.PlayerChunkSystem
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getSystem

class PlayerGlobalUpdateSystem : IteratingSystem(Aspect.all(NetworkSession::class, Viewport::class, Position::class, GlobalPlayers::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var globalPlayersMapper: ComponentMapper<GlobalPlayers>

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val globalPlayers = globalPlayersMapper.get(entityId)
        val players = world.getSystem(PlayerChunkSystem::class)
                .get(positionMapper.get(entityId))
                .filterNot { viewport.localPlayers().contains(it) }
                .sorted()
        globalPlayers.list.addAll(players)
    }

}