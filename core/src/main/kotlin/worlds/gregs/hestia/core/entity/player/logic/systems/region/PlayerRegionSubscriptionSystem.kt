package worlds.gregs.hestia.core.entity.player.logic.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.entity.player.model.events.PlayerRegionChanged
import worlds.gregs.hestia.core.world.movement.api.systems.RegionSubscription
import worlds.gregs.hestia.game.entity.Player

class PlayerRegionSubscriptionSystem : RegionSubscription(Player::class) {

    private lateinit var es: EventSystem

    override fun changeRegion(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(PlayerRegionChanged(entityId, from, to))
    }

}