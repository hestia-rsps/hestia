package worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.update.model.components.Moving
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.AddMobSync

class AddMobSyncFactory : SyncFactory<AddMobSync>(false, true, true) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var faceMapper: ComponentMapper<Face>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return bag.needsInsert(other) && bag.canInsert(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): AddMobSync {
        //Facing
        val face = faceMapper.get(other)
        //Position y
        val position = positionMapper.get(other)
        val delta = Position.delta(position, positionMapper.get(main))
        //Mob id
        val mobType = typeMapper.get(other)
        return AddMobSync.create(clientIndexMapper.get(other).index, face, true, delta, position.plane, mobType?.id
                ?: 0, movingMapper.has(other))
    }

}