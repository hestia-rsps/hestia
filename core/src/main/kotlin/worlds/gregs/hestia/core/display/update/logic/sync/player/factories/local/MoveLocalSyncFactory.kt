package worlds.gregs.hestia.core.display.update.logic.sync.player.factories.local

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.ViewportSystem.Companion.DEFAULT_VIEW_DISTANCE
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.MoveLocalPlayerSync
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement

class MoveLocalSyncFactory : SyncFactory<MoveLocalPlayerSync>(true, false, true) {

    private lateinit var movementMapper: ComponentMapper<Movement>
    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return movementMapper.get(other).actual == MovementType.Move
    }

    override fun create(main: Int, other: Int, update: Boolean): MoveLocalPlayerSync {
        val position = positionMapper.get(other)
        val mobile = mobileMapper.get(other)
        val global = !position.withinDistance(mobile, DEFAULT_VIEW_DISTANCE)
        return MoveLocalPlayerSync.create(position, mobile, global, update)
    }

}