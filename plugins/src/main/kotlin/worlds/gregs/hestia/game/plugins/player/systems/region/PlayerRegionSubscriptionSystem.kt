package worlds.gregs.hestia.game.plugins.player.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.api.movement.RegionSubscription
import worlds.gregs.hestia.game.api.player.Player
import worlds.gregs.hestia.game.events.PlayerRegionChanged

class PlayerRegionSubscriptionSystem : RegionSubscription(Player::class) {

    private lateinit var es: EventSystem

    override fun changeRegion(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(PlayerRegionChanged(entityId, from, to))
    }

}