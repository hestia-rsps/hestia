package worlds.gregs.hestia.game.plugins.mob.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.api.movement.systems.RegionChanged
import worlds.gregs.hestia.artemis.events.MobRegionChanged

class MobRegionChangeSystem : RegionChanged(Mob::class) {

    private lateinit var es: EventSystem

    override fun changedRegion(entityId: Int, from: Int, to: Int) {
        es.dispatch(MobRegionChanged(entityId, from, to))
    }
}