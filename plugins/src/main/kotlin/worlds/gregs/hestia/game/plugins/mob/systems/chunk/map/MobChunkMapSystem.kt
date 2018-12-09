package worlds.gregs.hestia.game.plugins.mob.systems.chunk.map

import worlds.gregs.hestia.game.api.mob.Mob
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.map.MobChunkMap

class MobChunkMapSystem : EntityChunkSystem(Mob::class) {

    private lateinit var map: MobChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}