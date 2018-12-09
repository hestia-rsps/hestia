package worlds.gregs.hestia.game.plugins.client.systems.update.stage

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag.BaseDisplayFlagSystem
import worlds.gregs.hestia.game.update.DisplayFlag
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.one

class GlobalDisplayFlagSystem : BaseDisplayFlagSystem(Aspect.all(NetworkSession::class, Position::class).one(GlobalPlayers::class, GlobalMobs::class)) {
    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    private lateinit var globalPlayersMapper: ComponentMapper<GlobalPlayers>
    private lateinit var globalMobsMapper: ComponentMapper<GlobalMobs>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun initialize() {
        super.initialize()
        addCheck(null) { _, other ->
            !world.entityManager.isActive(other)
        }

        addCheck(DisplayFlag.ADD) { player, other ->
            withinDistance(positionMapper.get(player), other)
        }
    }

    override fun process(entityId: Int) {
        val updates = entityUpdatesMapper.get(entityId)
        updates.map.putAll(check(entityId, globalPlayersMapper))
        updates.map.putAll(check(entityId, globalMobsMapper))
    }
}