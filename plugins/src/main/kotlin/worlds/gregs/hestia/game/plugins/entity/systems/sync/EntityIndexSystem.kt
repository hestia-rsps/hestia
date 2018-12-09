package worlds.gregs.hestia.game.plugins.entity.systems.sync

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.api.SubscriptionSystem
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.services.Aspect

/**
 * Doesn't really do anything, a back-up system for when player/mob plugins aren't attached
 * See [PlayerIndexSystem] or [MobIndexSystem]
 */
class EntityIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val entityList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..ENTITY_LIMIT).first { it > 0 && !entityList.contains(it) }
        if(clientIndexMapper.get(entityId).index == 0) {
            clientIndexMapper.get(entityId).index = index
            entityList.add(index)
        }
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        if(entityList.contains(clientIndex.index)) {
            entityList.remove(clientIndex.index)
        }
    }

    companion object {
        const val ENTITY_LIMIT = 4096
    }
}