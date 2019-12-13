package worlds.gregs.hestia.core.entity.mob.logic.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.entity.mob.model.events.MobRegionChanged
import worlds.gregs.hestia.core.world.movement.api.systems.RegionChanged

class MobRegionChangeSystem : RegionChanged(Mob::class) {

    private lateinit var es: EventSystem

    override fun changedRegion(entityId: Int, from: Int, to: Int) {
        es.dispatch(MobRegionChanged(entityId, from, to))
    }
}