package worlds.gregs.hestia.core.entity.npc.logic.systems.sync

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.entity.npc.api.Npc

class NpcIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Npc::class)) {

    class MaximumNpcLimitReached : Exception()

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val npcList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..NPCS_LIMIT)
                .firstOrNull { it > 0 && !npcList.contains(it) }
                ?: throw MaximumNpcLimitReached()
        clientIndexMapper.get(entityId).index = index
        npcList.add(index)
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        clientIndex.remove = true
        npcList.remove(clientIndex.index)
    }

    companion object {
        const val NPCS_LIMIT = 4096
    }
}