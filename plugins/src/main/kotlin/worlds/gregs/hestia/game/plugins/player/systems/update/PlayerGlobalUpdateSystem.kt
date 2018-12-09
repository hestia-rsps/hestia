package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.api.client.Client
import worlds.gregs.hestia.game.api.client.GlobalEntities
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.player.systems.chunk.map.PlayerChunkMapSystem
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getSystem

class PlayerGlobalUpdateSystem : IteratingSystem(Aspect.all(Client::class, Viewport::class, Position::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private var globalEntities: GlobalEntities? = null

    override fun checkProcessing(): Boolean {
        return globalEntities != null
    }

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val players = world.getSystem(PlayerChunkMapSystem::class)
                .get(positionMapper.get(entityId))
                .filterNot { viewport.localPlayers().contains(it) }
                .sorted()
        globalEntities?.addPlayers(entityId, players)
    }

}