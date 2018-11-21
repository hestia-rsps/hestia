package world.gregs.hestia.game.systems.sync.player

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.systems.sync.mob.MobIndexSystem

class MaximumPlayerLimitReached : Exception()

class PlayerIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Player::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    private val playerList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..ENTITY_LIMIT).first { it > 0 && !playerList.contains(it) }
        if(index > PLAYERS_LIMIT) {
            throw MaximumPlayerLimitReached()
        }
        clientIndexMapper.get(entityId).index = index
        playerList.add(index)
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        playerList.remove(clientIndex.index)
    }

    companion object {
        const val PLAYERS_LIMIT = 2048
        const val ENTITY_LIMIT = PlayerIndexSystem.PLAYERS_LIMIT + MobIndexSystem.MOBS_LIMIT
    }
}