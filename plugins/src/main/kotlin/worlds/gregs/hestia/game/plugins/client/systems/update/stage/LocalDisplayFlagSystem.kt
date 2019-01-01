package worlds.gregs.hestia.game.plugins.client.systems.update.stage

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag.BaseDisplayFlagSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.services.Aspect

class LocalDisplayFlagSystem : BaseDisplayFlagSystem(Aspect.all(NetworkSession::class, Position::class, Viewport::class)) {

    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun initialize() {
        super.initialize()
        addCheck(DisplayFlag.REMOVE) { player, other ->
            !world.entityManager.isActive(other) || !withinDistance(positionMapper.get(player), other)
        }

        addCheck(DisplayFlag.UPDATE) { player, other ->
            entityUpdatesMapper.get(player).list.contains(other)
        }
    }

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val updates = entityUpdatesMapper.get(entityId)
        updates.map.putAll(check(entityId, viewport.localPlayers()))
        updates.map.putAll(check(entityId, viewport.localMobs()))
    }
}