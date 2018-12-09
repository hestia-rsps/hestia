package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.api.client.Client
import worlds.gregs.hestia.game.api.client.GlobalEntities
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.mob.systems.chunk.map.MobChunkMapSystem
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getSystem

class MobGlobalUpdateSystem : IteratingSystem(Aspect.all(Client::class, Viewport::class, Position::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private var globalEntities: GlobalEntities? = null

    override fun checkProcessing(): Boolean {
        return globalEntities != null
    }

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val mobs = world.getSystem(MobChunkMapSystem::class)
                        .get(positionMapper.get(entityId))
                        .filterNot { viewport.localMobs().contains(it) }
                        .sorted()//Sort not required for mobs but makes loading nicer rather than in square chunks
        globalEntities?.addMobs(entityId, mobs)
    }

}