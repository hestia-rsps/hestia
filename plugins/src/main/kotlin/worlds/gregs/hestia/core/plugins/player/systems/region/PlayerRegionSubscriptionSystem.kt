package worlds.gregs.hestia.core.plugins.player.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.movement.systems.RegionSubscription
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.artemis.events.PlayerRegionChanged

class PlayerRegionSubscriptionSystem : RegionSubscription(Player::class) {

    private lateinit var es: EventSystem

    override fun changeRegion(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(PlayerRegionChanged(entityId, from, to))
    }

}