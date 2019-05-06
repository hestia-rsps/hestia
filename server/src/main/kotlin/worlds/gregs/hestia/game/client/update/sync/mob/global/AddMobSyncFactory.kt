package worlds.gregs.hestia.game.client.update.sync.mob.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.components.ClientIndex
import worlds.gregs.hestia.api.client.update.components.Moving
import worlds.gregs.hestia.api.client.update.components.direction.Face
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.entity.components.Type
import worlds.gregs.hestia.network.update.sync.mob.AddMobSync

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