package worlds.gregs.hestia.core.entity.mob.logic.systems.sync

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.artemis.Aspect

class MaximumMobLimitReached : Exception()

class MobIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Mob::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val mobList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..MOBS_LIMIT)
                .firstOrNull { it > 0 && !mobList.contains(it) }
                ?: throw MaximumMobLimitReached()
        clientIndexMapper.get(entityId).index = index
        mobList.add(index)
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        clientIndex.remove = true
        mobList.remove(clientIndex.index)
    }

    companion object {
        const val MOBS_LIMIT = 4096
    }
}