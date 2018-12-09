package worlds.gregs.hestia.game.plugins.mob.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.api.mob.Mob
import worlds.gregs.hestia.game.api.movement.RegionSubscription
import worlds.gregs.hestia.game.events.MobRegionChanged

class MobRegionSubscriptionSystem : RegionSubscription(Mob::class) {
    private lateinit var es: EventSystem

    override fun changeRegion(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(MobRegionChanged(entityId, from, to))
    }
}