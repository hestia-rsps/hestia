package worlds.gregs.hestia.game.plugins.player.systems.sync

import com.artemis.ComponentMapper
import worlds.gregs.hestia.GameConstants.PLAYERS_LIMIT
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.game.client.ClientIndex
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.services.Aspect

class MaximumPlayerLimitReached : Exception()

class PlayerIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class, Player::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var playerMapper: ComponentMapper<Player>

    private val playerList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val index = (1..PLAYERS_LIMIT)
                .firstOrNull { it > 0 && !playerList.contains(it) }
                ?: throw MaximumPlayerLimitReached()
        clientIndexMapper.get(entityId).index = index
        playerList.add(index)
    }

    override fun removed(entityId: Int) {
        val clientIndex = clientIndexMapper.get(entityId)
        playerList.remove(clientIndex.index)
    }
}