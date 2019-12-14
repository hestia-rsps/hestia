package worlds.gregs.hestia.core.entity.player.logic.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.world.movement.api.systems.RegionChanged
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.core.entity.player.model.events.PlayerRegionChanged

class PlayerRegionChangeSystem : RegionChanged(Player::class) {

    private lateinit var es: EventSystem

    override fun changedRegion(entityId: Int, from: Int, to: Int) {
        es.dispatch(PlayerRegionChanged(entityId, from, to))
    }

}