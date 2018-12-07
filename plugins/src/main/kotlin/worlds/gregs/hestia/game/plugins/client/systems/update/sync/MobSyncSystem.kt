package worlds.gregs.hestia.game.plugins.client.systems.update.sync

import com.artemis.ComponentMapper
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.EntitySync
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BaseMobSyncSystem
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.plugins.mob.component.Type
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.game.update.DisplayFlag

class MobSyncSystem : BaseMobSyncSystem(), EntitySync {

    override val localHandlers = HashMap<DisplayFlag, Packet.Builder.(Int, Int) -> Unit>()
    private var movingCheck: ((Int) -> Boolean)? = null
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var faceMapper: ComponentMapper<Face>
    private lateinit var positionMapper: ComponentMapper<Position>

    override fun start() {
        super.start()
        packet.writeBits(8, total)
    }

    override fun skip(locals: List<Int>, globals: List<Int>) {
        total = locals.size
        start()
        packet.writeBits(locals.size, 0)
        middle()
        total += globals.size
    }

    override fun local(entityId: Int, local: Int, type: DisplayFlag?, update: Boolean) {
        //Update?
        packet.writeBits(1, (type != null).int)
        if (type != null) {
            //Movement type
            packet.writeBits(2, if (type == DisplayFlag.REMOVE) 3 else type.movementType())

            //Handlers
            invokeLocal(packet, entityId, local, type)
        }
    }

    override fun global(entityId: Int, global: Int, type: DisplayFlag?, update: Boolean) {
        if (type != null) {
            //Client index
            packet.writeBits(15, clientIndexMapper.get(global).index)
            //Facing
            val face = faceMapper.get(global)
            val direction = if (face != null) DirectionUtils.getFaceDirection(face.x, face.y) else 0
            packet.writeBits(3, (direction shr 11) - 4)
            //Update
            packet.writeBits(1, update.int)
            //Position y
            val position = positionMapper.get(global)
            val delta = Position.delta(position, positionMapper.get(entityId))
            packet.writeBits(5, delta.y + if (delta.y < 15) 32 else 0)
            //Position z
            packet.writeBits(2, position.plane)
            //Mob id
            val mobType = typeMapper.get(global)
            packet.writeBits(15, mobType?.id ?: 0)//Mob id
            //Position x
            packet.writeBits(5, delta.x + if (delta.x < 15) 32 else 0)
            //Moving region
            packet.writeBits(1, (movingCheck != null).int)
        }
    }

    override fun process(entityId: Int) {
        super.process(entityId)

        packet.writeBits(15, 32767)
        packet.finishBitAccess()
    }

    fun addMoving(handler: (Int) -> Boolean) {
        movingCheck = handler
    }
}