package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.mob.systems.sync.MobChunkSystem
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getSystem

class MobGlobalUpdateSystem : IteratingSystem(Aspect.all(NetworkSession::class, Viewport::class, Position::class, GlobalMobs::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var globalMobsMapper: ComponentMapper<GlobalMobs>

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val globalMobs = globalMobsMapper.get(entityId)
        val mobs = world.getSystem(MobChunkSystem::class)
                        .get(positionMapper.get(entityId))
                        .filterNot { viewport.localMobs().contains(it) }
                        .sorted()//Sort not required for mobs but makes loading nicer rather than in square chunks
        globalMobs.list.addAll(mobs)
    }

}