package worlds.gregs.hestia.game.update.sync.mob.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.client.ClientIndex
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.entity.Type
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.components.Moving
import worlds.gregs.hestia.game.update.components.direction.Face
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.mob.AddMobSync

class AddMobSyncFactory : SyncFactory<AddMobSync>(false, true) {

    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: Synchronize, main: Int, other: Int): Boolean {
        return withinDistance(main, other)
    }

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var faceMapper: ComponentMapper<Face>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var movingMapper: ComponentMapper<Moving>

    override fun create(main: Int, other: Int, update: Boolean): AddMobSync {
        //Facing
        val face = faceMapper.get(other)
        val direction = if (face != null) DirectionUtils.getFaceDirection(face.x, face.y) else 0
        //Position y
        val position = positionMapper.get(other)
        val delta = Position.delta(position, positionMapper.get(main))
        //Mob id
        val mobType = typeMapper.get(other)
        return AddMobSync(clientIndexMapper.get(other).index, direction, true, delta, position.plane, mobType?.id
                ?: 0, movingMapper.has(other))
    }

}