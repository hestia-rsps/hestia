package worlds.gregs.hestia.game.plugins.player.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.api.movement.RegionChanged
import worlds.gregs.hestia.game.api.player.Player
import worlds.gregs.hestia.game.events.PlayerRegionChanged

class PlayerRegionChangeSystem : RegionChanged(Player::class) {

    private lateinit var es: EventSystem

    override fun changedRegion(entityId: Int, from: Int, to: Int) {
        es.dispatch(PlayerRegionChanged(entityId, from, to))
    }

}