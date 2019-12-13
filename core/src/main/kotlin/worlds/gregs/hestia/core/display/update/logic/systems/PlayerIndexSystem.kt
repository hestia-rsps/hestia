package worlds.gregs.hestia.core.display.update.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.GameConstants.PLAYERS_LIMIT
import worlds.gregs.hestia.artemis.BitQueue
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.update.api.IndexSystem
import worlds.gregs.hestia.game.entity.Player

class MaximumPlayerLimitReached : Exception()

open class PlayerIndexSystem : IndexSystem<Player>(Player::class) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val ids = HashMap<Int, Int>()//Map of all entity id's & client indexes
    private val removed = BitQueue()

    override fun inserted(entityId: Int) {
        val index = (1..PLAYERS_LIMIT)
                .firstOrNull { it > 0 && !ids.containsKey(it) }
                ?: throw MaximumPlayerLimitReached()//TODO some kind of login queue and response if world is full?
        clientIndexMapper.get(entityId).index = index
        ids[index] = entityId
    }

    override fun checkProcessing(): Boolean {
        return removed.size > 0
    }

    override fun processSystem() {
        while(removed.size > 0) {
            ids.remove(removed.poll()!!)
        }
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        clientIndex.remove = true
        removed.add(clientIndex.index)
    }

    override fun getId(index: Int): Int? {
        return ids[index]
    }
}