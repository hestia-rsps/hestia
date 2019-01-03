package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.client.GlobalEntities
import worlds.gregs.hestia.api.client.components.Client
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.api.player.PlayerChunk
import worlds.gregs.hestia.services.Aspect

class PlayerGlobalUpdateSystem : IteratingSystem(Aspect.all(Client::class, Viewport::class, Position::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private var globalEntities: GlobalEntities? = null
    private lateinit var chunkMapSystem: PlayerChunk

    override fun checkProcessing(): Boolean {
        return globalEntities != null
    }

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val players = chunkMapSystem
                .get(positionMapper.get(entityId))
                .filterNot { viewport.localPlayers().contains(it) }
                .sorted()
        globalEntities?.addPlayers(entityId, players)
    }

}