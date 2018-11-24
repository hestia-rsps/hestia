package worlds.gregs.hestia.game.plugins.mob.systems.sync

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.systems.extensions.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.mob.component.Mob
import worlds.gregs.hestia.game.plugins.player.systems.sync.PlayerIndexSystem.Companion.ENTITY_LIMIT
import worlds.gregs.hestia.services.Aspect

class MaximumMobLimitReached : Exception()

class MobIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Mob::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val mobList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..ENTITY_LIMIT).first { it > 0 && !mobList.contains(it) }
        if(index > worlds.gregs.hestia.game.plugins.mob.systems.sync.MobIndexSystem.Companion.MOBS_LIMIT) {
            throw worlds.gregs.hestia.game.plugins.mob.systems.sync.MaximumMobLimitReached()
        }
        clientIndexMapper.get(entityId).index = index
        mobList.add(index)
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        mobList.remove(clientIndex.index)
    }

    companion object {
        const val MOBS_LIMIT = 4096
    }
}