package worlds.gregs.hestia.game.mob.systems.sync

import com.artemis.Component
import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.mob.component.MobViewDistance
import worlds.gregs.hestia.game.systems.sync.ViewDistanceSystem

class MobViewDistanceSystem : ViewDistanceSystem(MobChunkSystem::class, MAXIMUM_MOB_VIEW_DISTANCE, MAXIMUM_LOCAL_MOBS, MobViewDistance::class) {

    private lateinit var mobViewDistanceMapper: ComponentMapper<MobViewDistance>

    override fun getMapper(): ComponentMapper<out Component> {
        return mobViewDistanceMapper
    }

    companion object {
        const val MAXIMUM_MOB_VIEW_DISTANCE = 15
        const val MAXIMUM_LOCAL_MOBS = 255
    }
}
