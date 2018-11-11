package world.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import world.gregs.hestia.GameConstants
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.entity.Player
import world.gregs.hestia.game.systems.extensions.SubscriptionSystem
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.services.one

class ClientIndexSystem : SubscriptionSystem(Aspect.all(ClientIndex::class).one(Mob::class, Player::class)) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var mobMapper: ComponentMapper<Mob>

    private val mobList = ArrayList<Int>()
    private val playerList = ArrayList<Int>()

    override fun inserted(entityId: Int) {
        val list = if(mobMapper.has(entityId)) mobList else playerList
        val index = (1..GameConstants.PLAYERS_LIMIT).first { it > 0 && !list.contains(it) }
        clientIndexMapper.get(entityId).index = index
        list.add(index)
    }

    override fun removed(entityId: Int) {
        val list = if(mobMapper.has(entityId)) mobList else playerList
        val clientIndex = clientIndexMapper.get(entityId)
        list.remove(clientIndex.index)
    }
}