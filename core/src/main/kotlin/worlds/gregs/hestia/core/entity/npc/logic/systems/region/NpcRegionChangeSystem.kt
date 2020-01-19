package worlds.gregs.hestia.core.entity.npc.logic.systems.region

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.entity.npc.api.Npc
import worlds.gregs.hestia.core.entity.npc.model.events.NpcRegionChanged
import worlds.gregs.hestia.core.world.movement.api.systems.RegionChanged

class NpcRegionChangeSystem : RegionChanged(Npc::class) {

    private lateinit var es: EventSystem

    override fun changedRegion(entityId: Int, from: Int, to: Int) {
        es.dispatch(NpcRegionChanged(entityId, from, to))
    }
}