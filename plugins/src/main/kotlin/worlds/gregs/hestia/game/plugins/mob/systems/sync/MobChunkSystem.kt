package worlds.gregs.hestia.game.plugins.mob.systems.sync

import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap
import worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem
import worlds.gregs.hestia.game.plugins.mob.component.Mob
import worlds.gregs.hestia.game.plugins.mob.systems.MobChunkMap

class MobChunkSystem : EntityChunkSystem(Mob::class) {

    private lateinit var map: MobChunkMap

    override fun getMap(): EntityChunkMap {
        return map
    }
}