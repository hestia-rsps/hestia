package world.gregs.hestia.game.systems.sync

import com.artemis.ComponentMapper
import world.gregs.hestia.game.update.UpdateStage
import world.gregs.hestia.game.component.*
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.update.DirectionUtils.Companion.getMobMoveDirection
import world.gregs.hestia.services.send
import world.gregs.hestia.game.component.map.Position

class MobSyncSystem : MobUpdateSystem(Aspect.all(NetworkSession::class, Renderable::class, Viewport::class)) {

    private lateinit var es: EventSystem
    private lateinit var packet: Packet.Builder
    private lateinit var data: Packet.Builder
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>

    override fun begin(entityId: Int) {
        //Reset
        packet = Packet.Builder(6, Packet.Type.VAR_SHORT)
        data = Packet.Builder()

        //Start
        val viewport = viewportMapper.get(entityId)
        packet.startBitAccess()
        packet.writeBits(8, viewport.localMobs().size)
    }

    /**
     * Process local mobs
     */
    override fun local(entityId: Int, local: Int, type: UpdateStage, iterator: MutableIterator<Int>) {
        //Update
        if (type == UpdateStage.UPDATE) {
            update(entityId, local, data, false, false)
        }
        //Sync
        val needsUpdate = flags.any { t -> t.subscription.entities.contains(local) }
        packet.writeBits(1, if (type == UpdateStage.REMOVE) 0 else needsUpdate.int)//Update?
        packet.writeBits(2, type.movementType())//Movement type

        when (type) {
            UpdateStage.MOVE -> {
                iterator.remove()
            }
            UpdateStage.WALKING, UpdateStage.RUNNING -> {
                val running = runMapper.has(entityId)

                if (running) {
                    packet.writeBits(1, 1)
                }

                val nextWalkDirection = walkMapper.get(local).direction
                packet.writeBits(3, getMobMoveDirection(nextWalkDirection))

                if (running) {
                    val nextRunDirection = runMapper.get(local).direction
                    packet.writeBits(3, getMobMoveDirection(nextRunDirection))
                }

                packet.writeBits(1, needsUpdate.int)
            }
            UpdateStage.UPDATE -> {
                packet.writeBits(2, 0)
            }
            else -> {
            }
        }
    }

    /**
     * Process other mobs
     */
    override fun global(entityId: Int, global: Int, type: UpdateStage, iterator: MutableIterator<Int>) {
        //Update
        if (type == UpdateStage.ADD) {
            update(entityId, global, data, false, true)
        }
        //Sync
        if (type != UpdateStage.SKIP) {
            packet.writeBits(15, clientIndexMapper.get(global).index)
            packet.writeBits(3, (0 shr 11) - 4)
            packet.writeBits(1, flags.any { t -> t.subscription.entities.contains(global) }.int)
            val position = positionMapper.get(global)
            val delta = Position.delta(position, positionMapper.get(entityId))
            packet.writeBits(5, delta.y + if (delta.y < 15) 32 else 0)
            packet.writeBits(2, position.plane)
            packet.writeBits(15, 0)//Mob type id
            packet.writeBits(5, delta.x + if (delta.x < 15) 32 else 0)
            packet.writeBits(1, movingMapper.has(global).int)
            val viewport = viewportMapper.get(entityId)
            viewport.addLocalMob(global)
        }
    }

    override fun end(entityId: Int) {

        //Write update data
        if (data.buffer.writerIndex() > 0) {
            packet.writeBits(15, 32767)
            packet.finishBitAccess()
            packet.writeBytes(data.buffer)
        } else {
            packet.finishBitAccess()
        }

        es.send(entityId, packet)
    }
}