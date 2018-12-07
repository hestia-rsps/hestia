package worlds.gregs.hestia.game.plugins.player.systems.map

import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityRegionChangeSystem
import worlds.gregs.hestia.game.plugins.player.component.Player
import worlds.gregs.hestia.game.plugins.region.systems.RegionEntitySystem

class PlayerRegionChangeSystem : EntityRegionChangeSystem(Position::class, Player::class) {

    private lateinit var regionEntities: RegionEntitySystem

    override fun changedRegion(entityId: Int, from: Int, to: Int) {
        regionEntities.remove(from)
        regionEntities.add(to)
    }
}