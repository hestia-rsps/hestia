package worlds.gregs.hestia.game.plugins.player.systems.map

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.player.component.Player
import worlds.gregs.hestia.game.plugins.region.systems.RegionEntitySystem
import worlds.gregs.hestia.services.Aspect

class PlayerRegionSystem : SubscriptionSystem(Aspect.all(Position::class, Player::class)) {

    private lateinit var regionEntities: RegionEntitySystem
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun inserted(entityId: Int) {
        val position = positionMapper.get(entityId)
        regionEntities.add(position.regionId)
    }

    override fun removed(entityId: Int) {
        println("Player removed $entityId")
        val position = positionMapper.get(entityId)
        regionEntities.remove(position.regionId)
    }
}