package worlds.gregs.hestia.game.plugins.client.systems.update

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.api.client.GlobalEntities
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers

class GlobalEntitySystem : GlobalEntities() {
    private lateinit var globalPlayersMapper: ComponentMapper<GlobalPlayers>
    private lateinit var globalMobsMapper: ComponentMapper<GlobalMobs>

    override fun addPlayers(entityId: Int, players: List<Int>) {
        if(globalPlayersMapper.has(entityId)) {
            globalPlayersMapper.get(entityId).list.addAll(players)
        }
    }

    override fun addMobs(entityId: Int, mobs: List<Int>) {
        if(globalMobsMapper.has(entityId)) {
            globalMobsMapper.get(entityId).list.addAll(mobs)
        }
    }

}
