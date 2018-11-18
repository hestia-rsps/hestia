package world.gregs.hestia.game.systems.sync.mob

import com.artemis.ComponentMapper
import world.gregs.hestia.GameConstants
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.game.systems.sync.player.PlayerIndexSystem.Companion.ENTITY_LIMIT
import world.gregs.hestia.services.Aspect

class MaximumMobLimitReached : Exception()

class MobIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Mob::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val mobList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..ENTITY_LIMIT).first { it > 0 && !mobList.contains(it) }
        if(index > MOBS_LIMIT) {
            throw MaximumMobLimitReached()
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