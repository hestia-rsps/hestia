package worlds.gregs.hestia.core.plugins.mob.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.api.movement.systems.RegionSubscription
import worlds.gregs.hestia.artemis.events.MobRegionChanged

class MobRegionSubscriptionSystem : RegionSubscription(Mob::class) {
    private lateinit var es: EventSystem

    override fun changeRegion(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(MobRegionChanged(entityId, from, to))
    }
}