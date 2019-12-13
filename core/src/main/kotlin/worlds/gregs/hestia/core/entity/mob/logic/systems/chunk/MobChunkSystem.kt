package worlds.gregs.hestia.core.entity.mob.logic.systems.chunk

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.mob.model.events.MobChunkChanged
import worlds.gregs.hestia.core.entity.mob.logic.systems.chunk.map.MobChunkMap

class MobChunkSystem : PassiveSystem() {

    private lateinit var map: MobChunkMap

    @Subscribe
    private fun event(event: MobChunkChanged) {
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