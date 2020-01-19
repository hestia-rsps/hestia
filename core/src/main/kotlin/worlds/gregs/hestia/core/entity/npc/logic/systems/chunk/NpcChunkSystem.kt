package worlds.gregs.hestia.core.entity.npc.logic.systems.chunk

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.npc.logic.systems.chunk.map.NpcChunkMap
import worlds.gregs.hestia.core.entity.npc.model.events.NpcChunkChanged

class NpcChunkSystem : PassiveSystem() {

    private lateinit var map: NpcChunkMap

    @Subscribe
    private fun event(event: NpcChunkChanged) {
        changed(event.entityId, event.from, event.to)
    }

    private fun changed(entityId: Int, from: Int?, to: Int?) {
        if(from != null) {
            map.remove(from, entityId)
        }
        if (to != null) {
            map.add(to, entityId)
        }
    }
}