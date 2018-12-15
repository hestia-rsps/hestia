package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.client.GlobalEntities
import worlds.gregs.hestia.api.client.components.Client
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.api.mob.MobChunk
import worlds.gregs.hestia.services.Aspect

class MobGlobalUpdateSystem : IteratingSystem(Aspect.all(Client::class, Viewport::class, Position::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private var globalEntities: GlobalEntities? = null
    private lateinit var chunkMapSystem: MobChunk

    override fun checkProcessing(): Boolean {
        return globalEntities != null
    }

    override fun process(entityId: Int) {
        val viewport = viewportMapper.get(entityId)
        val mobs = chunkMapSystem
                        .get(positionMapper.get(entityId))
                        .filterNot { viewport.localMobs().contains(it) }
                        .sorted()//Sort not required for mobs but makes loading nicer rather than in square chunks
        globalEntities?.addMobs(entityId, mobs)
    }

}