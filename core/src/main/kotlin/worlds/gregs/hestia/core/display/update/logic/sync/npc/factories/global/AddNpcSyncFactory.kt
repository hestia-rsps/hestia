package worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.global

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.AddNpcSync
import worlds.gregs.hestia.core.display.update.model.components.direction.Facing
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.world.movement.model.MovementType
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement

class AddNpcSyncFactory : SyncFactory<AddNpcSync>(false, true, true) {

    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var movementMapper: ComponentMapper<Movement>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun change(sync: SynchronizeSystem<*>, bag: EntitySyncBag, other: Int): Boolean {
        return bag.needsInsert(other) && bag.canInsert(other)
    }

    override fun create(main: Int, other: Int, update: Boolean): AddNpcSync {
        //Facing
        val face = facingMapper.get(other)
        //Position y
        val position = positionMapper.get(other)
        val delta = Position.delta(position, positionMapper.get(main))
        //Npc id
        val npcType = typeMapper.get(other)
        return AddNpcSync.create(clientIndexMapper.get(other).index, face, true, delta, position.plane, npcType?.id ?: 0, movementMapper.get(other)?.actual == MovementType.Move)
    }

}