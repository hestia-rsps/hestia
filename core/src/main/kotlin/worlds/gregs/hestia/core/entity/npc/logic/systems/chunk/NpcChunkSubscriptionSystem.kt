package worlds.gregs.hestia.core.entity.npc.logic.systems.chunk

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.core.entity.npc.api.Npc
import worlds.gregs.hestia.core.entity.npc.model.events.NpcChunkChanged
import worlds.gregs.hestia.core.world.movement.api.systems.ChunkSubscription

class NpcChunkSubscriptionSystem : ChunkSubscription(Npc::class) {
    private lateinit var es: EventSystem

    override fun changedChunk(entityId: Int, from: Int?, to: Int?) {
        es.dispatch(NpcChunkChanged(entityId, from, to))
    }
}